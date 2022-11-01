package com.khulud.gathering.fragmnets


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gathering.R
import com.example.gathering.databinding.FragmentEditeProfileBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.khulud.gathering.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

// Add & Update fragment

class EditeProfileFragment : Fragment() {


    private var binding: FragmentEditeProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()

    private var imageUri: Uri? = null
    private var storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private val profilesCollection = Firebase.firestore.collection("profiles")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditeProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayInfo()

        binding!!.saveBtn.setOnClickListener {
            val profile = getProfileInfo()
            val checkInfo = checkInfo(profile)


            if (checkInfo) {
                saveProfileInfo(profile)

                if (imageUri != null) {
                    lifecycleScope.launch {
                        uploadImage()
                    }
                }

            }


        }

        binding!!.profilePic.setOnClickListener {
            pickImagesIntint()
        }


    }
    // region upload image to firebase

    private suspend fun uploadImage() {
        Log.e("TAG", "uploadImage: $imageUri")
        val ref = storageReference.child("UserProfileImage/" + UUID.randomUUID().toString())
        val uploadTask = ref.putFile(imageUri!!)
        withContext(Dispatchers.IO) {
            uploadTask.continueWithTask(
                Continuation<UploadTask.TaskSnapshot, Task<Uri>>
                { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())
                } else {
                    binding!!.profilePic.setImageResource(R.drawable.ic_baseline_person_24)
                }
            }

        }

    }
    // endregion

    // region add photo to DB
    private fun addUploadRecordToDb(uri: String) {
        val userId = viewModel.currentUserID()

        val data = mapOf("imageUrl" to uri)

        profilesCollection.document(userId).update(data)
            .addOnSuccessListener {
                Toast.makeText(this.requireContext(), "Saved to DB $userId", Toast.LENGTH_LONG)
                    .show()

            }
            .addOnFailureListener {
                Toast.makeText(this.requireContext(), "Error saving to DB", Toast.LENGTH_LONG)
                    .show()
            }
    }
    //endregion

    // region add photo from Gallery
    fun pickImagesIntint() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), 456)
    }
    // endregion

    // region save Profile Info
    private fun saveProfileInfo(profile: Profiles) {
        val profileData = mapOf(
            "userid" to profile.userid,
            "bio" to profile.bio,
            "username" to profile.username
        )
        Firebase.firestore.collection("profiles").document(profile.userid).set(profileData,
            SetOptions.merge())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.e("TAG", "saveProfileInfo: ${profile.imageUrl}")
                    Toast.makeText(this.context, "Save", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.action_editeProfileFragment_to_profileFragment)
                } else {
                    Toast.makeText(this.context, "Try again", Toast.LENGTH_LONG).show()
                }
            }

    }
    //endregion

    // region return a Profile Info
    private fun getProfileInfo(): Profiles {

        val userId = viewModel.currentUserID()
        val username = binding!!.usernameInput.text.toString()
        val bio = binding!!.bioInput.text.toString()



        return Profiles(userId, username, bio)
    }

    //endregion

    // region check if Info NOT empty
    private fun checkInfo(profile: Profiles): Boolean {

        if (profile.username.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter First Name", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (profile.bio.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter Your Bio", Toast.LENGTH_SHORT)
                .show()
            return false
        } else {
            return true
        }
    }
    //endregion

    // region get Info to Display
    private fun displayInfo() {
        viewModel.username.observe(viewLifecycleOwner, { binding!!.usernameInput.setText(it) })

        viewModel.bio.observe(viewLifecycleOwner, { binding!!.bioInput.setText(it) })

// add pic visibility
    }
    //endregion

    //region display selected image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e("TAG", "onActivityResult: $requestCode == 456")
        if (requestCode == 456) {

            imageUri = data?.data!!
            Log.e("TAG", "onActivityResult:imageUri $imageUri")
            binding?.profilePic?.setImageURI(imageUri)
            try {

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    //endregion

}

