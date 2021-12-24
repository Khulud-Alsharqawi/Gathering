package com.khulud.gathering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.gathering.R
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userId = intent.getStringExtra("user_id")
        val logoutButton = findViewById<Button>(R.id.logout)

        findViewById<TextView>(R.id.userId).text = userId
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        }

    }
}