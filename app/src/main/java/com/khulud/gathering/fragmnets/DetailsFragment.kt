package com.khulud.gathering.fragmnets

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentDetailsBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue.delete
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khulud.gathering.model.*
import com.khulud.gathering.utility.ui.bindImage

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private val viewModel: EventsViewModel by viewModels()
    lateinit var eventName: String
    lateinit var imageUrl: String
    lateinit var eventsLocation: String
    lateinit var eventDescription: String
    lateinit var price: String
    lateinit var argu: String
    lateinit var date: String

    private val favDB = Firebase.firestore.collection("BookmarkEventsList")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            eventName = it.getString(EVENTNAME).toString()
            imageUrl = it.getString(IMAGEURL).toString()
            eventsLocation = it.getString(EVENTSLOCATION).toString()
            eventDescription = it.getString(EVENTDESCRIPTION).toString()
            price = it.getString(PRICE).toString()
            date = it.getString(DATE).toString()


        }
        /**
        ? * check the states of the bookmark tag ------------------------------------------------
         **/
        binding?.btnBookmarkFull?.isVisible = checkBookmarkItem(eventName) == true
        if (checkBookmarkItem(eventName)) {
            binding?.btnBookmarkFull?.setImageResource(R.drawable.ic_baseline_fullbookmark)
        } else {
            binding?.btnBookmarkBorder?.setImageResource(R.drawable.ic_baseline_emptybookmark)
        }

        /**
        ? * check the states of the bookmark tag ------------------------------------------------
         **/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentDetailsFragment = FragmentDetailsBinding.inflate(inflater, container, false)
        binding = fragmentDetailsFragment
        binding?.apply {
            viewModel = this@DetailsFragment.viewModel
            lifecycleOwner = this@DetailsFragment.viewLifecycleOwner
            detailsFragment = this@DetailsFragment
        }


        return fragmentDetailsFragment.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // region glid
        binding?.imageViewEvent?.bindImage(viewModel.imageUrl.value)
        arguments.let {
            argu = it?.getString("eventName").toString()
        }
        //endregion
        getEventsByName(argu)
        // region share button
        binding?.btnShare?.setOnClickListener {
            Toast.makeText(this.requireContext(), "the button works", Toast.LENGTH_SHORT)
                .show()
            shareEvent()
        }
        //endregion
        // region location button
        binding?.btnLocation?.setOnClickListener {
            Toast.makeText(
                this.requireContext(),
                "the button of location works",
                Toast.LENGTH_SHORT
            )
                .show()
            getLocation()
        }
        //endregion
        //  region book mark button
        binding?.btnBookmarkBorder?.setOnClickListener {
            binding?.btnBookmarkFull?.isVisible = true
            isBookmark()
        }
        //endregion
        //region bookmark full button
        binding?.btnBookmarkFull?.setOnClickListener {
            binding?.btnBookmarkFull?.isVisible = false
            deletBookedItem()
        }
        //endregion
        //region booking button
        binding?.btnBookig?.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this.requireContext())

            // set message of alert dialog
            val negativeButton =
                    dialogBuilder.setMessage("Do you want to book to attend this event?")
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton(
                            "Proceed",
                            DialogInterface.OnClickListener { dialog, id ->
                                findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)

                            })
                        // negative button text and action
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Reservation Confirmation")
            // show alert dialog
            alert.show()
        }
        //endregion
    }

    /*
    ! function that delete the doc id
    */
    private fun deletBookedItem() {
/*!---------------------------------------------------------------------------*/
        if (checkBookmarkItem(eventName) == true) {
             favDB.document().delete()

        }


    }
    // region Location

    fun getLocation() {

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(viewModel.eventsLocation.value)
        )
        startActivity(intent)
    }
    //endregion
    //region share event

    fun shareEvent() {

        val intent = Intent(Intent.ACTION_SEND)
            .putExtra(
                Intent.EXTRA_TEXT,
                " I like to visit this event $eventName , come with me :)"
            )
            .setType("text/plain")
        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }
    }

    //endregion
    companion object {
        const val EVENTNAME = "eventName"
        const val IMAGEURL = "imageUrl"
        const val EVENTSLOCATION = "eventsLocation"
        const val EVENTDESCRIPTION = "eventDescription"
        const val PRICE = "price"
        const val DATE = "eventDate"
    }
    //region get event by name

    fun getEventsByName(name: String) {


        Firebase.firestore.collection("Events-DB").whereEqualTo("eventName", name)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result.documents) {
                        Log.d("TAG: ", "${documentSnapshot.data?.get("eventImage")}")
                        viewModel.eventName.value =
                                documentSnapshot.data?.get("eventName").toString()
                        viewModel.imageUrl.value =
                                documentSnapshot.data?.get("eventImage").toString()
                        viewModel.eventDescription.value =
                                documentSnapshot.data?.get("eventInfo").toString()
                        viewModel.eventsLocation.value =
                                documentSnapshot.data?.get("eventLocation").toString()
                        viewModel.price.value =
                                documentSnapshot.data?.get("price").toString()
                        viewModel.eventDate.value =
                                documentSnapshot.data?.get("eventDate").toString()

                    }
                } else {
                }
            })

    }

    //endregion
    //region get event by name = true

    fun getEventsByNameT(name: String): EventsList {
        Firebase.firestore.collection("Events-DB").whereEqualTo("eventName", name)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result.documents) {
                        Log.d("TAG: ", "${documentSnapshot.data?.get("eventImage")}")

                        viewModel.eventName.value =
                                documentSnapshot.data?.get("eventName").toString()

                        viewModel.imageUrl.value =
                                documentSnapshot.data?.get("eventImage").toString()

                        viewModel.eventDescription.value =
                                documentSnapshot.data?.get("eventInfo").toString()

                        viewModel.eventsLocation.value =
                                documentSnapshot.data?.get("eventLocation").toString()

                        viewModel.price.value =
                                documentSnapshot.data?.get("price").toString()

                        viewModel.eventDate.value =
                                documentSnapshot.data?.get("eventDate").toString()
                    }
                }
            })
        var name = viewModel.eventName.value!!
        var image = viewModel.imageUrl.value!!
        var description = viewModel.eventDescription.value!!
        var location = viewModel.eventsLocation.value!!
        var price = viewModel.price.value!!
        var date = viewModel.eventDate.value!!
        return EventsList(image, name, description, location, true, price, date)

    }
    //endregion
    //region getUserId

    private fun getUserId(): String {
        return Firebase.auth.currentUser!!.uid
    }

    //endregion
    //region addToBookmarkList
    private fun addToBookmarkList(bookmarkEventItem: BookmarkEventsList) {

        favDB.add(bookmarkEventItem)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    Toast.makeText(this.requireContext(), "Bookmark", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this.requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }


    }
    //endregion
    //region getbookmark item

    private fun getBookmarkItem(userId: String, eventItem: EventsList): BookmarkEventsList {
        return BookmarkEventsList(userId, eventItem)
    }

    //endregion
    //region is booked mark
    private fun isBookmark() {
        var id = getUserId()
        var eventsList = getEventsByNameT(argu)
        var isBookMarked = checkBookmarkItem(argu)
        var bookmarkEventItem = getBookmarkItem(id, eventsList)
        if (isBookMarked == true) {
            /*! delete */
            Toast.makeText(this.requireContext(), "Item is booked already", Toast.LENGTH_SHORT).show()
            deletBookedItem()
        } else {
            addToBookmarkList(bookmarkEventItem)
        }
    }

    //endregion
    //region check booked mark item
    fun checkBookmarkItem(name: String): Boolean {
        var isFav = false
        //  var nameList = mutableListOf<String>()
        favDB.whereEqualTo("eventName", name)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result.documents) {
                        /* nameList.add(documentSnapshot.data?.get("eventName").toString())
//                        Log.e(
//                            "TAG",
//                            "checkBookmarkItem: ${
//                                documentSnapshot.data?.get("eventName").toString()
//                            } ",
//                        )
//                        Log.d(" Massege", "checkBookmarkItem() called with: task = $task") */
                        if (documentSnapshot.data?.get("userUid").toString() == getUserId()) {
                            if (documentSnapshot.data?.get("eventName").toString() == name) {
                                Log.d(
                                    " Massege",
                                    "checkBookmarkItem() called with: task = ${
                                        documentSnapshot.data?.get("eventName").toString()
                                    }"
                                )
                                isFav = true
                            }

                        }
                    }
                } else {

                }
            })

        return isFav
    }
    //endregion

}