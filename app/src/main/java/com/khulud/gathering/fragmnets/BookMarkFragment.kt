package com.khulud.gathering.fragmnets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentBookMarkBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khulud.gathering.adapter.BookmarkAdapter
import com.khulud.gathering.model.*

class BookMarkFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
    var binding: FragmentBookMarkBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isSignIn()) {
            findNavController().navigate(R.id.action_bookMarkFragment_to_startingFragment)
            Toast.makeText(this.requireContext(), "You Should Be Login First", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBookMarkBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        profileViewModel.bookmark.observe(
            viewLifecycleOwner, {
                val adapter = BookmarkAdapter(it)
                binding?.eventBookmarksRecycleView?.adapter = adapter
            }
        )

        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = profileViewModel


    }

    private fun isSignIn(): Boolean {
        val currentUser = Firebase.auth.currentUser

        return currentUser != null
    }

}
