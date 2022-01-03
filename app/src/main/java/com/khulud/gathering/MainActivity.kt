package com.khulud.gathering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.gathering.R
import com.example.gathering.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.khulud.gathering.adapter.EventsAdapter
import com.khulud.gathering.fragmnets.HomeFragment
import com.khulud.gathering.model.EventsList


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        val navMenu = findViewById<NavigationView>(R.id.nav_view)
        navMenu.setupWithNavController(navController)
        //  setupActionBarWithNavController(navController)
        //    val topAppBar = binding.topAppBar

        //    topAppBar.setOnClickListener {

//            navMenu.open
        // Handle navigation icon press
    }

}