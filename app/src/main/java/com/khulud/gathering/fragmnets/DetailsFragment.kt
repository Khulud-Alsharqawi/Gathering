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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.gathering.R
import com.example.gathering.databinding.FragmentDetailsBinding
import com.example.gathering.databinding.FragmentStartingBinding
import com.google.android.gms.common.api.Api
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.khulud.gathering.model.EventsViewModel
import com.khulud.gathering.utility.ui.bindImage
import kotlinx.coroutines.flow.combine

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private val viewModel: EventsViewModel by viewModels()
    lateinit var eventName: String
    lateinit var imageUrl: String
    lateinit var eventsLocation: String
    lateinit var eventDescription: String
    lateinit var  argu :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionMenu(true)

        arguments?.let {

            eventName = it.getString(EVENTNAME).toString()
            imageUrl = it.getString(IMAGEURL).toString()
            eventsLocation = it.getString(EVENTSLOCATION).toString()
            eventDescription = it.getString(EVENTDESCRIPTION).toString()
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
//        (activity as AppCompatActivity).supportActionBar?.title = "Details"


        //glid
        binding?.imageViewEvent?.bindImage(viewModel.imageUrl.value)
        arguments.let {
          argu = it?.getString("eventName").toString()

        }
        getEventsByName( argu)
        //share button
        binding?.btnShare?.setOnClickListener {
            Toast.makeText(this.requireContext(), "the button works", Toast.LENGTH_SHORT)
                .show()
            shareEvent()
        }

        binding?.btnLocation?.setOnClickListener{
            Toast.makeText(this.requireContext(), "the button of location works", Toast.LENGTH_SHORT)
                .show()
            getLocation()

        }

    }

//    fun Glide(): String? {
//        if (view == eventName){
//            val images = view.eventImage
//            val imgUri = images.toUri().buildUpon().build()
//            Glide.with(holder.imageView)
//                .load(imgUri)
//                .into(holder.imageView)
//        }
//    }

    fun getLocation(){

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
                " I like to visit this event $eventName , come with me :)"  )
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
    }

    fun getEventsByName(name: String) {


            Firebase.firestore.collection("Events-DB").whereEqualTo ("eventName", name)
            .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            Log.d("TAG: " , "${documentSnapshot.data?.get("eventImage")}")
                            viewModel.eventName.value = documentSnapshot.data?.get("eventName").toString()
                            viewModel.imageUrl.value = documentSnapshot.data?.get("eventImage").toString()
                            viewModel.eventDescription.value = documentSnapshot.data?.get("eventInfo").toString()
                            viewModel.eventsLocation.value = documentSnapshot.data?.get("eventLocation").toString()

                        }
                    } else {
                    }
                })

        }


    }
