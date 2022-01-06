package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gathering.databinding.FragmentHomeBinding
import com.google.firebase.firestore.*
import com.khulud.gathering.adapter.EventsAdapter
import com.khulud.gathering.model.EventsList
import com.khulud.gathering.model.EventsViewModel


class HomeFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    private val viewModel: EventsViewModel by viewModels()
    private var binding: FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentHomeBinding
        binding?.lifecycleOwner = this
        // adapter with data on parameter
        binding?.eventsRecycleView?.adapter = EventsAdapter(EventChangeListener())

        binding?.eventsRecycleView?.setHasFixedSize(true)

        binding?.viewModel = viewModel

        return fragmentHomeBinding.root

        this.EventChangeListener()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val userId = intent.getStringExtra("user_id")
//       findViewById<TextView>(R.id.userId).text = userId


    }

    private fun EventChangeListener(): ArrayList<EventsList> {
        var eventsArrayList: ArrayList<EventsList> = ArrayList()
        db = FirebaseFirestore.getInstance()
        db.collection("Events-DB").orderBy("eventName", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Fire Store Error: ", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            eventsArrayList.add(dc.document.toObject(EventsList::class.java))
                        }
                    }
                    binding?.eventsRecycleView?.adapter?.notifyDataSetChanged()
                }

            })

        return eventsArrayList

    }


}




