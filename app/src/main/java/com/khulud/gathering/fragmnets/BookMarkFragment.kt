package com.khulud.gathering.fragmnets

import android.annotation.SuppressLint
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
import com.example.gathering.databinding.FragmentBookMarkBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.khulud.gathering.adapter.BookmarkAdapter
import com.khulud.gathering.model.*

class BookMarkFragment : Fragment() {

    private val viewModel: EventsViewModel by viewModels()
    var binding: FragmentBookMarkBinding? = null
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isSignIn()) {
            findNavController().navigate(R.id.action_bookMarkFragment_to_startingFragment)
            Toast.makeText(this.requireContext(), "You Should Be Login First", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentBookMarkBinding = FragmentBookMarkBinding.inflate(inflater, container, false)
        binding = fragmentBookMarkBinding
        binding?.lifecycleOwner = this
        // adapter with data on parameter
        binding?.eventBookmarksRecycleView?.adapter = BookmarkAdapter(BookMarkEventChangeListener())

        binding?.eventBookmarksRecycleView?.setHasFixedSize(true)

        binding?.viewModel = viewModel

        return fragmentBookMarkBinding.root

        this.BookMarkEventChangeListener()

    }

    private fun BookMarkEventChangeListener(): ArrayList<EventsList> {

        val currentUserId = Firebase.auth.currentUser?.uid

        var eventsArrayList: ArrayList<EventsList> = ArrayList()

        db.collection("profiles").whereEqualTo("userUid", currentUserId)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(
                    value: QuerySnapshot?, error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Fire Store Error: ", error.message.toString())
                        return
                    } else
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                eventsArrayList.add(dc.document.toObject(EventsList::class.java))
                            }
                        }
                    binding?.eventBookmarksRecycleView?.adapter?.notifyDataSetChanged()
                }

            })

        return eventsArrayList


    }

    private  fun isSignIn() : Boolean {
        val currentUser = Firebase.auth.currentUser

        if (currentUser != null)
            return true
        else
            return false
    }

}
