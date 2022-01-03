package com.khulud.gathering.fragmnets

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.gathering.R
import com.example.gathering.databinding.FragmentDetailsBinding
import com.example.gathering.databinding.FragmentStartingBinding
import com.khulud.gathering.model.EventsViewModel
import kotlinx.coroutines.flow.combine

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private val viewModel: EventsViewModel by viewModels()
    lateinit var eventName: String
    lateinit var imageUrl: String
    lateinit var eventsLocation: String
    lateinit var eventDescription: String


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
        (activity as AppCompatActivity).supportActionBar?.title = "Details"
        viewModel.eventName.value = eventName
       // viewModel.imageUrl.value = imageUrl
        viewModel.eventDescription.value = eventDescription
        viewModel.eventsLocation.value = eventsLocation

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

    fun getLocation(){

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("google.navigation:q=replace+this+with+an+address")
        )
        startActivity(intent)
    }

    fun shareEvent() {
/*        val intent = Intent(Intent.ACTION_SEND).putExtra(
//            Intent.EXTRA_TEXT,
//            "I'm visiting ${viewModel.eventName.value}" +
//                    viewModel.event.value?.find { it.eventName == viewModel.eventName.value }!!.eventName
//        )
//            .setType("text/plain")
//        if (activity?.packageManager?.resolveActivity(intent, 0) != null) startActivity(intent) */
        val intent = Intent(Intent.ACTION_SEND)
            .putExtra(
                Intent.EXTRA_TEXT,
                " test"
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
    }
}