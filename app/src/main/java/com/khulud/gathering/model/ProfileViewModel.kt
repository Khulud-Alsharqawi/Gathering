package com.khulud.gathering.model

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception


class ProfileViewModel : ViewModel() {


    private var _profile = MutableLiveData<List<Profiles>>()
    val profile: MutableLiveData<List<Profiles>> get() = _profile

    private var _bio = MutableLiveData<String>()
    val bio: MutableLiveData<String> get() = _bio

    private var _username = MutableLiveData<String>()
    val username: MutableLiveData<String> get() = _username

    private var _proImage = MutableStateFlow("")
    val proImage: LiveData<String> get() = _proImage.asLiveData()

    private var _bookmark = MutableLiveData<List<EventsList>>()
    val bookmark: LiveData<List<EventsList>> get() = _bookmark

    init {
        getProfilerByUserId()
        getProfileInfo()
    }


    fun currentUserID(): String {
        return Firebase.auth.currentUser!!.uid
    }

//
    private fun getProfilerByUserId() {
        val userId = currentUserID()
        viewModelScope.launch {
            Firebase.firestore.collection("profiles").document(userId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                      val userdata= task.result.toObject(Profiles::class.java)
                            _username.value = userdata?.username
                            _bio.value = userdata?.bio
                            _proImage.update {userdata?.imageUrl?:""}
                           _bookmark.value=userdata?.bookMarks
                        }
                    }
                }
        }





    class DeleteFirebase {

        fun delete() {
            val db = FirebaseFirestore.getInstance()
            Firebase.auth

            db.collection("profiles").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .delete()
                .addOnSuccessListener {
                    Log.e("TAG", "save: true")

                }
                .addOnFailureListener { e ->
                    Log.e("TAG", "save: error $e")

                }
        }

    }


    fun getProfileInfo() {
        val userId = Firebase.auth.currentUser!!.uid

        try {
            Firebase.firestore.collection("profiles").document(userId).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = it.result.toObject(Profiles::class.java)
                        _bookmark.value = user?.bookMarks
                    }


                }
        } catch (e: Exception) {
            Log.d("TAG", "getProfileInfo: ${e.message}")
        }
    }
}