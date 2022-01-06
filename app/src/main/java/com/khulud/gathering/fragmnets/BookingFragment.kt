package com.khulud.gathering.fragmnets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentBookingBinding


class BookingFragment : Fragment() {
    private var binding: FragmentBookingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBookingBinding = FragmentBookingBinding.inflate(inflater, container, false)
        binding= fragmentBookingBinding
        return fragmentBookingBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnCancel?.setOnClickListener {
          //  Toast.makeText(this.requireContext(), "btnCancel ", Toast.LENGTH_SHORT).show()
 findNavController().navigate(R.id.action_bookingFragment_to_homeFragment)
        }

        binding?.btnSend?.setOnClickListener {
            Toast.makeText(
                this.requireContext(),
                "You Have booked successfully ", Toast.LENGTH_SHORT ).show()
            findNavController().navigate(R.id.action_bookingFragment_to_homeFragment)

        }
    }

}