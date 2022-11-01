package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentStartingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class StartingFragment : Fragment() {
    private var binding: FragmentStartingBinding? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentStartingBinding = FragmentStartingBinding.inflate(inflater, container, false)
        binding = fragmentStartingBinding
        return fragmentStartingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isSignIn()
      binding?.btnToSignIn?.setOnClickListener{
          findNavController().navigate(R.id.starting_to_signin)

      }

        binding?.btnToReg?.setOnClickListener {
            findNavController().navigate(R.id.action_startingFragment_to_registerFragment)
        }

        binding?.btnTologout?.setOnClickListener {
            signout()
        }

        binding?.btnToProfile?.setOnClickListener {
            findNavController().navigate(R.id.action_startingFragment_to_profileFragment)
        }

    }

    private fun signout() {
        binding?.btnTologout?.editableText.toString()
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(R.id.action_startingFragment_to_homeFragment)

    }

    private fun isSignIn() {

        val currentUser = Firebase.auth.currentUser

        if (currentUser != null){
          binding?.btnToSignIn?.isGone = true
            binding?.btnToReg?.isGone = true
        }else {
            binding?.btnToProfile?.isGone = true
            binding?.btnTologout?.isGone = true
        }

    }
}