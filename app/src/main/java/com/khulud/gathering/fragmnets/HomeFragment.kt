package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.google.firebase.auth.FirebaseAuth
import com.example.gathering.databinding.FragmentHomeBinding
import com.google.firebase.firestore.*
import com.khulud.gathering.adapter.EventsAdapter
import com.khulud.gathering.model.EventsList
import com.khulud.gathering.model.EventsViewModel


class HomeFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    // lateinit var toggle:ActionBarDrawerToggle
    private val viewModel: EventsViewModel by viewModels()
    private var binding: FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
//        binding.
//        val navView:NavgationView

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

       binding?.viewModel=  viewModel

        return fragmentHomeBinding.root

        this.EventChangeListener()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val userId = intent.getStringExtra("user_id")
//       findViewById<TextView>(R.id.userId).text = userId
        binding?.logout?.setOnClickListener {
            signout()
        }

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

    fun signout() {
        binding?.logout?.editableText.toString()
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(R.id.action_homeFragment_to_startingFragment)

    }
}




