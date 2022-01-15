package com.khulud.gathering.fragmnets


import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gathering.R
import com.example.gathering.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.khulud.gathering.model.ProfileViewModel
import com.khulud.gathering.utility.ui.bindImage
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch


//View Profile Fragment

class ProfileFragment : Fragment() {
    lateinit var userProfile: String
    lateinit var argu: String
    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()
    //val s = "https://firebasestorage.googleapis.com/v0/b/gathering-c4a13.appspot.com/o/UserProfileImage%2F2a677fb1-e589-48b3-981c-07d637d8af37?alt=media&token=0c5bc3dc-77e8-4db5-afea-ec9227c27787"
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
    ): View? {
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
//        binding?.uploadGalaryPic?.bindImage(viewModel.proImage.value)
        arguments.let {
            Log.e("TAG", "onCreate: title111111 ${it?.getString("userid").toString()}")
            argu = it?.getString("userid").toString()
            Log.e("TAG", "onCreate: title22222 ${it?.getString("userid").toString()}")

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
           findNavController().navigate(R.id.action_profileFragment_to_startingFragment)

        }
    }

    fun delete(){
        ProfileViewModel.DeleteFirebase().delete()
        removeUser()


    }

    fun removeUser(){
        val user = Firebase.auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }

    }

    companion object {

        const val USERPROFILE = "userProfile"

    }


    // region get Info to Display
    private fun displayInfo(userid: String) {
        viewModel.username.observe(viewLifecycleOwner, { binding!!.usernameOutput.setText(it) })
        Log.d(TAG, "displayInfo() called with: userid = $userid")
        viewModel.bio.observe(viewLifecycleOwner, { binding!!.bioOutput.setText(it) })

        lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.proImage.observe(viewLifecycleOwner,{
                it.let {
//                binding!!.uploadGalaryPic.setImageURI(it.toUri())}
                    Glide.with(this@ProfileFragment.requireContext())
                        .load(it).placeholder(R.drawable.loading_animation).into(binding!!.uploadGalaryPic)}
            })
            Log.d(TAG, "displayInfo() called with: userid = $userid")
        }
        }
    }
//endregion


//    private fun setImage (image : String) : Uri?{
//        val imgUri = image.toUri().buildUpon().build()
//        Glide.with(binding!!.uploadGalaryPic)
//            .load(imgUri)
//            .placeholder(R.drawable.loading_animation)
//            .into(binding!!.uploadGalaryPic)
//
//        return imgUri
//    }

}


