package com.khulud.gathering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gathering.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.btn_login)
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email =findViewById<EditText>(R.id.login_email).text.toString()
        val password =findViewById<EditText>(R.id.login_password).text.toString()

        if (email.isNotEmpty()&& password.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this@LoginActivity,"Logged in", Toast.LENGTH_SHORT).show()
                        val intent= Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("user_id", firebaseUser.uid)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity,"Error", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener{
                    println(it.message)
                }
        }
    }
}