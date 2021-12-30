package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import com.example.gathering.databinding.FragmentStartingBinding


class StartingFragment : Fragment() {
    private var binding: FragmentStartingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentStartingBinding = FragmentStartingBinding.inflate(inflater, container, false)
        binding = fragmentStartingBinding
        return fragmentStartingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      binding?.btnText1?.setOnClickListener{
          findNavController().navigate(com.example.gathering.R.id.starting_to_signin)


      }

        binding?.btnText2?.setOnClickListener {
            findNavController().navigate(com.example.gathering.R.id.action_startingFragment_to_registerFragment)
        }


    }
}