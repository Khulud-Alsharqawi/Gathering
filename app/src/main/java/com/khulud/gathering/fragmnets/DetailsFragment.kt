package com.khulud.gathering.fragmnets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gathering.R
import com.example.gathering.databinding.FragmentDetailsBinding
import com.example.gathering.databinding.FragmentStartingBinding
import kotlinx.coroutines.flow.combine

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentDetailsFragment = FragmentDetailsBinding.inflate(inflater, container, false)
    binding= fragmentDetailsFragment
        return fragmentDetailsFragment.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}