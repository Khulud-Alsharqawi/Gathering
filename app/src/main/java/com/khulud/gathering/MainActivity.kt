package com.khulud.gathering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gathering.R
import com.google.android.gms.common.internal.Objects
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerButton = findViewById<Button>(R.id.btn_reg)
        val loginLink = findViewById<TextView>(R.id.goToLogin)


        registerButton.setOnClickListener{
            registerUser()
        }
        loginLink.setOnClickListener{
         startActivity(Intent(this@MainActivity, LoginActivity::class.java))

        }
    }

    private fun registerUser() {
        val email =findViewById<EditText>(R.id.reg_email).text.toString()
        val password =findViewById<EditText>(R.id.reg_password).text.toString()

        if (email.isNotEmpty()&& password.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                       val firebaseUser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this@MainActivity,"Logged in",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this@MainActivity, HomeActivity::class.java)
                        intent.putExtra("user_id", firebaseUser.uid)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener{
                    println(it.message)
                }
        }
    }
}