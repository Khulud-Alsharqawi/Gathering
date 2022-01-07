package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentBookingBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khulud.gathering.model.EventsList
import com.khulud.gathering.model.EventsViewModel


class BookingFragment : Fragment() {
    private var binding: FragmentBookingBinding? = null
    private val viewModel:EventsViewModel by viewModels()
    lateinit var eventName: String
    lateinit var price: String
    lateinit var argu: String
    lateinit var date:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            eventName = it.getString(DetailsFragment.EVENTNAME).toString()
            price = it.getString(DetailsFragment.PRICE).toString()
            date=it.getString(DetailsFragment.DATE).toString()

        }    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBookingBinding = FragmentBookingBinding.inflate(inflater, container, false)
        binding = fragmentBookingBinding
        return fragmentBookingBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnCancel?.setOnClickListener {
            //  Toast.makeText(this.requireContext(), "btnCancel ", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_bookingFragment_to_homeFragment)
        }

        arguments.let {
            argu = it?.getString("eventName").toString()

        }
        getEventsByName(argu)

        binding?.btnSend?.setOnClickListener {
            Toast.makeText(
                this.requireContext(),
                "You Have booked successfully ", Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_bookingFragment_to_homeFragment)

        }
    }


    fun getEventsByName(name: String) {


        Firebase.firestore.collection("Events-DB").whereEqualTo("eventName", name)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result.documents) {
                        Log.e("TAG: ", "${documentSnapshot.data?.get("eventImage")}")
                        viewModel.eventName.value =
                                documentSnapshot.data?.get("eventName").toString()
                        viewModel.price.value =
                                documentSnapshot.data?.get("price").toString()
                        viewModel.eventDate.value =
                                documentSnapshot.data?.get("eventDate").toString()
                        Log.e("TAG: ", "${documentSnapshot.data?.get("eventName")}")

                    }
                } else {
                    Log.e("TAGG", "getEventsByName: No data", )
                }
            })

    }

/*    fun getEventsByNameT(name: String): EventsList {
//
//        Firebase.firestore.collection("Events-DB").whereEqualTo("eventName", name)
//            .get()
//            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
//                if (task.isSuccessful) {
//                    for (documentSnapshot in task.result.documents) {
//                        Log.d("TAG: ", "${documentSnapshot.data?.get("eventImage")}")
//
//                        viewModel.eventName.value =
//                                documentSnapshot.data?.get("eventName").toString()
//
//                        viewModel.price.value =
//                                documentSnapshot.data?.get("price").toString()
//
//                        viewModel.eventDate.value =
//                                documentSnapshot.data?.get("eventDate").toString()
//                    }
//                }
//            })
//        var name = viewModel.eventName.value!!
//        var price = viewModel.price.value!!
//        var date=viewModel.eventDate.value!!
//        return EventsList( name, price, date)
//
//    } */





}