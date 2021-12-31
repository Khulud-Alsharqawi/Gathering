package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding = fragmentRegisterBinding
        return fragmentRegisterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.btnReg?.setOnClickListener {
            regstration()

        }

    }

    fun regstration() {
        val regEmail = binding?.regEmail?.editableText.toString()
        val regPassword = binding?.regPassword?.editableText.toString()

        if (regEmail.isNotEmpty() && regPassword.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(regEmail, regPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this.requireContext(), "Logged in", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_registerFragment_to_signInFragment)

                    } else {
                        Toast.makeText(this.requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    println(it.message)
                }
        }


    }
}