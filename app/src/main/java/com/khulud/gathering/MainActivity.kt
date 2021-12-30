package com.khulud.gathering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.gathering.R
import com.example.gathering.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

//        setContentView(R.layout.activity_main)

        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)




    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

//    }
//    val registerButton = findViewById<Button>(R.id.btn_reg)
//    val loginLink = findViewById<TextView>(R.id.goToLogin)
//
//    registerButton.setOnClickListener{
//        registerUser()
//    }
//    loginLink.setOnClickListener{
//        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
//
//    }
////    private fun registerUser() {
//        val email =findViewById<EditText>(R.id.reg_email).text.toString()
//        val password =findViewById<EditText>(R.id.reg_password).text.toString()
//        if (email.isNotEmpty()&& password.isNotEmpty()){
//            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener { task->
//                    if (task.isSuccessful){
//                       val firebaseUser: FirebaseUser = task.result!!.user!!
//                        Toast.makeText(this@MainActivity,"Logged in",Toast.LENGTH_SHORT).show()
//                        val intent=Intent(this@MainActivity, HomeActivity::class.java)
//                        intent.putExtra("user_id", firebaseUser.uid)
//                        startActivity(intent)
//                        finish()
//                    }else{
//                        Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_SHORT).show()
//                    }
//                }
//                .addOnFailureListener{
//                    println(it.message)
//                }
//        }
//    }
