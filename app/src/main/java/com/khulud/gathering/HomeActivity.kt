package com.khulud.gathering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gathering.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject

import com.khulud.gathering.model.EventsList


class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    // private lateinit var navController: NavController

    lateinit var recycleView: RecyclerView

    val eventDL = mutableListOf<EventsList>()

//    val adapter = EventsAdapter(eventDL, applicationContext)

    // private lateinit var referenceDB: DatabaseReference

    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.e("TAG", "Docmmmmmmmmmmmm 1")
        firestoreDb = FirebaseFirestore.getInstance()
        Log.e("TAG", "Docmmmmmmmmmmmm 2")
        val postRef = firestoreDb.collection("Events-DB")
        postRef.addSnapshotListener { snapshot, e ->
            for (documents in snapshot!!.documents) {
                Log.e("TAG", "Docmmmmmmmmmmmm 3")

                val allEvents = documents.toObject<EventsList>()
                Log.e("TAG", "documents is $allEvents")
                if (allEvents != null) {
                    eventDL.add(allEvents)
                }
            }
            Log.e("OUT OF FOR LOOOP: ", "the list contains = ${eventDL}")
        }

        Log.e("TAG", "Docmmmmmmmmmmmm 4")
/*        val navHostFragment = supportFragmentManager
//
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//        setupActionBarWithNavController(this, navController) */

        recycleView = findViewById(R.id.eventsRecycleView)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.setHasFixedSize(true)
        Log.e("TAG", "Docmmmmmmmmmmmm 5")
        // Log.e("TAG", "before adapter ${eventDL}")
//        val adapter = EventsAdapter(eventDL,applicationContext)
//        recycleView.adapter = adapter
        Log.e("TAG", "Docmmmmmmmmmmmm 6")
        val userId = intent.getStringExtra("user_id")
        val logoutButton = findViewById<Button>(R.id.logout)
        Log.e("TAG", "Docmmmmmmmmmmmm 7")
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recycleView.addItemDecoration(decoration)
        recycleView.setHasFixedSize(true)
        Log.e("TAG", "Docmmmmmmmmmmmm 8")
        /* referenceDB=
 /               FirebaseDatabase.getInstance("https://console.firebase.google.com/project/gathering-c4a13/firestore/data/~2FEvents-DB~2F0pMYLtpQcTlOa0nu1dpj")
 //                    .getReference()*/
        Log.e("TAG", "Docmmmmmmmmmmmm 9")
        findViewById<TextView>(R.id.userId).text = userId
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
            Log.e("TAG", "Docmmmmmmmmmmmm 10")
            //dataFB
            listAllFiles()
        }

        /*       fun onSupportNavigateUp(): Boolean {
                   return navController.navigateUp() || super.onSupportNavigateUp()
               } */

    }
    //   val dataFB: Unit
//        get() {
//            var referenceDB: DatabaseReference
//            referenceDB.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (mountainRef in dataSnapshot.children) {
//                        val eventDataList = mountainRef.getValue(EventsList::class.java)
//                        if (eventDataList != null) {
//                            eventDL.add(eventDataList)
//                        }
//                    }
//
//                    val adaper = EventsAdapter(eventDL, applicationContext)
//                    recycleView.adapter = adaper
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.w(" Error TAG ", "DatabaseError")
//                }
//            })
//
//        }

    private fun listAllFiles() {
        Log.e("TAG", "Docmmmmmmmmmmmm 11")
        firestoreDb = FirebaseFirestore.getInstance()
        Log.e("TAG", "Docmmmmmmmmmmmm 12")
        firestoreDb.collection("Events-DB")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?, error: FirebaseFirestoreException?
                ) {  Log.e("TAG", "Docmmmmmmmmmmmm 13")
                    if (error != null) {
                        return
                    }
                    for (de: DocumentChange in value?.documentChanges!!) {
                        if (de.type == DocumentChange.Type.ADDED) {
                            eventDL.add(de.document.toObject(EventsList::class.java))
                        }
                    }
                    Log.e("TAG", "Docmmmmmmmmmmmm 14")
//                    adapter.notifyDataSetChanged()
                }

            })
    }

}

