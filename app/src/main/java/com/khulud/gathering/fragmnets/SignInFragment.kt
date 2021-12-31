package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignInFragment : Fragment() {
    private var binding: FragmentSignInBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val loginButton = findViewById<Button>(R.id.btn_login)
//        binding.btnSign.setOnClickListener {
//
//        }
//        loginButton.setOnClickListener {
//            loginUser()
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)
        binding = fragmentSignInBinding
        return fragmentSignInBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  val email =findViewById<EditText>(R.id.login_email).text.toString()
        binding?.btnLogin?.setOnClickListener {
            signIn()
        }
    }

    fun signIn() {
        val email = binding?.loginEmail?.editableText.toString()

        val password = binding?.loginPassword?.editableText.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this.requireContext(), "Logged in", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                    } else {
                        Toast.makeText(this.requireContext(), "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .addOnFailureListener {
                    println(it.message)
                }
        }
    }
}
