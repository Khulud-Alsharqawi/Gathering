package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.gathering.R
import com.example.gathering.databinding.FragmentBookMarkBinding
import com.example.gathering.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.khulud.gathering.adapter.BookmarkAdapter
import com.khulud.gathering.adapter.EventsAdapter
import com.khulud.gathering.model.BookmarkEvents
import com.khulud.gathering.model.BookmarkEventsList
import com.khulud.gathering.model.EventsList
import com.khulud.gathering.model.EventsViewModel

class BookMarkFragment : Fragment() {

    private val viewModel: EventsViewModel by viewModels()
    var binding : FragmentBookMarkBinding? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var userId : String




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val fragmentBookMarkBinding = FragmentBookMarkBinding.inflate(inflater, container, false)
        binding = fragmentBookMarkBinding
        binding?.lifecycleOwner = this
        // adapter with data on parameter
        binding?.eventBookmarksRecycleView?.adapter = BookmarkAdapter(BookMarkEventChangeListener())

        binding?.eventBookmarksRecycleView?.setHasFixedSize(true)

        binding?.viewModel=  viewModel

        return fragmentBookMarkBinding.root

        this.BookMarkEventChangeListener()

    }

    private fun BookMarkEventChangeListener() : ArrayList<BookmarkEventsList> {


        var eventsArrayList: ArrayList<BookmarkEventsList> = ArrayList()
        db = FirebaseFirestore.getInstance()
        userId = Firebase.auth.currentUser!!.uid

        db.collection("BookmarkEventsList").whereEqualTo("userUid",userId)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Fire Store Error: ", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            eventsArrayList.add(dc.document.toObject(BookmarkEventsList::class.java))
                        }
                    }
                    binding?.eventBookmarksRecycleView?.adapter?.notifyDataSetChanged()
                }

            })

        return eventsArrayList



    }

}
