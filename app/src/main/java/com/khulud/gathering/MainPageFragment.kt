//package com.khulud.gathering
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.gathering.R
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.khulud.gathering.model.EventsList
//
//
//class MainPageFragment : Fragment() {
//
//    lateinit var recycleView: RecyclerView
//    private lateinit var eventDL: List<EventsList>
//    lateinit var refDB: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_home)
////         val userId = intent.getStringExtra("user_id")
////        val logoutButton = findViewById<Button>(R.id.logout)
////
////      findViewById<TextView>(R.id.userId).text = userId
////        logoutButton.setOnClickListener {
////            FirebaseAuth.getInstance().signOut()
////            startActivity(Intent(this, MainActivity::class.java))
//
//        }
//
//    override fun onCreateView(
//        inflater: L ayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.activity_home, container, false)
//        val userId = intent.getStringExtra("user_id")
//        val logoutButton = view?.findViewById<Button>(R.id.logout)
//
//        view?.findViewById<TextView>(R.id.userId)?.text = userId
//        logoutButton?.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//           // startActivity(Intent(this, MainActivity::class.java))
//        }
//
//
//    }
//}