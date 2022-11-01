package com.khulud.gathering.fragmnets


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gathering.R
import com.example.gathering.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khulud.gathering.model.ProfileViewModel
import kotlinx.coroutines.launch


//View Profile Fragment

class ProfileFragment : Fragment() {
    private lateinit var userProfile: String
    lateinit var argu: String
    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            userProfile = it?.getString(USERPROFILE).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        binding = fragmentProfileBinding
        binding?.apply {
            viewModel = this@ProfileFragment.viewModel
            lifecycleOwner = this@ProfileFragment.viewLifecycleOwner
            profileFragment = this@ProfileFragment
        }
        return fragmentProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // region glid
        arguments.let {
            argu = it?.getString("userid").toString()

        }
        //endregion

        displayInfo(argu)
        // on click update
        binding!!.updateBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editeProfileFragment)
        }

        binding!!.settingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }

        binding!!.deleteBtn.setOnClickListener {
            delete()

        }
    }

    private fun delete() {
        ProfileViewModel.DeleteFirebase().delete()
        removeUser()


    }

    private fun removeUser() {
        val user = Firebase.auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")

                    FirebaseAuth.getInstance().signOut()

                    findNavController().navigate(R.id.action_profileFragment_to_startingFragment)

                }
            }

    }

    companion object {

        const val USERPROFILE = "userProfile"

    }


    // region get Info to Display
    private fun displayInfo(userid: String) {
        viewModel.username.observe(viewLifecycleOwner, { binding!!.usernameOutput.text = it })
        Log.d(TAG, "displayInfo() called with: userid = $userid")
        viewModel.bio.observe(viewLifecycleOwner, { binding!!.bioOutput.text = it })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.proImage.observe(viewLifecycleOwner, {
                    it.let {
                        Glide.with(this@ProfileFragment.requireContext())
                            .load(it)
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .into(binding!!.uploadGalaryPic)
                    }
                })
                Log.d(TAG, "displayInfo() called with: userid = $userid")
            }
        }
    }
//endregion
}


