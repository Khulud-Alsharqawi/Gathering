package com.khulud.gathering.fragmnets

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentDetailsBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
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
    private lateinit var eventsLocation: String
    private lateinit var eventDescription: String
    private lateinit var price: String
    private lateinit var date: String
    lateinit var argu: String


    private val favDB = Firebase.firestore.collection("profiles")


   // @RequiresApi(Build.VERSION_CODES.Q)
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

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        arguments.let {
            argu = it?.getString("eventName").toString()

        }
        getEventsByName(argu)

        // region glid
        binding?.imageViewEvent?.bindImage(viewModel.imageUrl.value)
        arguments.let {
            argu = it?.getString("eventName").toString()
        }
        //endregion
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
            binding?.btnBookmarkFull?.setImageResource(R.drawable.ic_baseline_fullbookmark)

            val bookMark = EventsList(
                eventImage = viewModel.imageUrl.value!!,
                eventName = viewModel.eventName.value!!,
                eventInfo = viewModel.eventDescription.value!!,
                eventLocation = viewModel.eventsLocation.value!!,
                price = viewModel.price.value!!,
                eventDate = viewModel.eventDate.value!!,

                )
            addToBookmarkList(bookMark)
        }
        //endregion
        //region bookmark full button
        binding?.btnBookmarkFull?.setOnClickListener {
            binding?.btnBookmarkFull?.isVisible = false
            binding?.btnBookmarkBorder?.setImageResource(R.drawable.ic_baseline_emptybookmark)
            deleteBookedItem(eventName)
        }
        //endregion
        //region booking button
        binding?.btnBookig?.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this.requireContext())

            // set message of alert dialog

            dialogBuilder.setMessage("Do you want to book to attend this event?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton(
                    "Proceed"
                ) { _, _ ->
                    findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)

                }
                // negative button text and action
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Reservation Confirmation")
            // show alert dialog
            alert.show()
        }
        //endregion
    }


    // region deleteBookedItem

    private fun deleteBookedItem(eventName: String) {
        favDB.document(getUserId())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // bring the profile "data class" to deal with the object and add the
                    val user = task.result.toObject(Profiles::class.java)
                    user!!.bookMarks.removeIf { it.eventName == eventName }

                    favDB.document(getUserId()).update("bookMarks", user.bookMarks)
                    binding?.btnBookmarkBorder?.setImageResource(R.drawable.ic_baseline_emptybookmark)
                    Toast.makeText(this.requireContext(), "REMOVED", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this.requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }

    }
    //endregion

   // region Location

    private fun getLocation() {

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(viewModel.eventsLocation.value)
        )
        startActivity(intent)
    }
    //endregion

    //region share event

    private fun shareEvent() {

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


    //region get event by name

    private fun getEventsByName(name: String) {


        Firebase.firestore.collection("Events-DB").whereEqualTo("eventName", name)
            .get()
            .addOnCompleteListener { task ->
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
            }

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
    private fun addToBookmarkList(bookMark: EventsList) {

        favDB.document(getUserId())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // bring the profile "data class" to deal with the object and add the
                    val user = task.result.toObject(Profiles::class.java)
                    user?.bookMarks?.add(bookMark)
                    favDB.document(getUserId()).update("bookMarks", user?.bookMarks)
                    binding?.btnBookmarkFull?.setImageResource(R.drawable.ic_baseline_fullbookmark)
                    Toast.makeText(this.requireContext(), "Booked", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this.requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }


    }


    //endregion

    //region check booked mark item
    private fun checkBookmarkItem(): Boolean {

        favDB.document(getUserId())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // bring the profile "data class" to deal with the object and add the
                    val user = task.result.toObject(Profiles::class.java)
                    user!!.bookMarks.find { it.eventName == eventName }


                    favDB.document(getUserId()).update("bookMarks", user.bookMarks)
                    binding?.btnBookmarkBorder?.setImageResource(R.drawable.ic_baseline_emptybookmark)
                    Toast.makeText(this.requireContext(), "REMOVED", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this.requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }
   return true }
    //endregion
    companion object {
        const val EVENTNAME = "eventName"
        const val IMAGEURL = "imageUrl"
        const val EVENTSLOCATION = "eventsLocation"
        const val EVENTDESCRIPTION = "eventDescription"
        const val PRICE = "price"
        const val DATE = "eventDate"
      //  const val BOOKEDEVENTS = "bookMarks"
    }


}