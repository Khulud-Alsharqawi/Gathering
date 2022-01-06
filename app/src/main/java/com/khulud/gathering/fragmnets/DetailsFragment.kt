package com.khulud.gathering.fragmnets

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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
    lateinit var eventsLocation: String
    lateinit var eventDescription: String
    lateinit var price: String
    lateinit var argu: String


    private val favDB = Firebase.firestore.collection("BookmarkEventsList")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionMenu(true)

        arguments?.let {

            eventName = it.getString(EVENTNAME).toString()
            imageUrl = it.getString(IMAGEURL).toString()
            eventsLocation = it.getString(EVENTSLOCATION).toString()
            eventDescription = it.getString(EVENTDESCRIPTION).toString()
            price = it.getString(PRICE).toString()

        }
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

        //glid
        binding?.imageViewEvent?.bindImage(viewModel.imageUrl.value)
        arguments.let {
            argu = it?.getString("eventName").toString()

        }
        getEventsByName(argu)
        //share button
        binding?.btnShare?.setOnClickListener {
            Toast.makeText(this.requireContext(), "the button works", Toast.LENGTH_SHORT)
                .show()
            shareEvent()
        }

        binding?.btnLocation?.setOnClickListener {
            Toast.makeText(
                this.requireContext(),
                "the button of location works",
                Toast.LENGTH_SHORT
            )
                .show()
            getLocation()

        }

        // book mark
        binding?.btnBookmarkBorder?.setOnClickListener {
            binding?.btnBookmarkFull?.isVisible = true
            isBookmark()
        }

        binding?.btnBookmarkFull?.setOnClickListener {
            binding?.btnBookmarkFull?.isVisible = false

        }

        binding?.btnBookig?.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_bookingFragment)
        }

    }


    fun getLocation() {

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(viewModel.eventsLocation.value)
        )
        startActivity(intent)
    }

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

    companion object {
        const val EVENTNAME = "eventName"
        const val IMAGEURL = "imageUrl"
        const val EVENTSLOCATION = "eventsLocation"
        const val EVENTDESCRIPTION = "eventDescription"
        const val PRICE = "price"
    }

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

                    }
                } else {
                }
            })

    }

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
                    }
                }
            })
        var name = viewModel.eventName.value!!
        var image = viewModel.imageUrl.value!!
        var description = viewModel.eventDescription.value!!
        var location = viewModel.eventsLocation.value!!
        var price = viewModel.price.value!!
        return EventsList(image, name, description, location, true, price)

    }

    private fun getUserId(): String {
        return Firebase.auth.currentUser!!.uid
    }

    private fun addToBookmarkList(bookmarkEventItem: BookmarkEventsList) {

        favDB.add(bookmarkEventItem)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    Toast.makeText(this.requireContext(), "Bookmark", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this.requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }


    }

    private fun getBookmarkItem(userId: String, eventItem: EventsList): BookmarkEventsList {
        return BookmarkEventsList(userId, eventItem)
    }

    private fun isBookmark() {

        var id = getUserId()
        var eventsList = getEventsByNameT(argu)
        var test = checkBookmarkItem(argu)
        var bookmarkEventItem = getBookmarkItem(id, eventsList)

        if (test == false) {
            addToBookmarkList(bookmarkEventItem)
        } else
            Toast.makeText(this.requireContext(), "Item is booked already", Toast.LENGTH_SHORT)
                .show()

    }

    fun checkBookmarkItem(name: String): Boolean {

                var nameList = mutableListOf<String>()

        favDB.whereEqualTo("eventName", name)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result.documents) {
                        nameList.add(documentSnapshot.data?.get("eventName").toString())
                        Log.e(
                            "TAG",
                            "checkBookmarkItem: ${
                                documentSnapshot.data?.get("eventName").toString()
                            } ",
                        )
                        Log.d(" Massege", "checkBookmarkItem() called with: task = $task")
                    }
                }
            })

        return true
    }
}