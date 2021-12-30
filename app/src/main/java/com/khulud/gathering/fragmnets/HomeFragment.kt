package com.khulud.gathering.fragmnets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    val fragmentHomeBinding =FragmentHomeBinding.inflate(inflater,container,false)
        binding =fragmentHomeBinding
        return fragmentHomeBinding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnDetails?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
        }

        binding?.btnProfile?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        binding?.btnLog?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_startingFragment)
        }
    }


}