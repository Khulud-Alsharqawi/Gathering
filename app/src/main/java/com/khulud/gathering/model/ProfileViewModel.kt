package com.khulud.gathering.model

import android.util.Log
import androidx.core.view.isGone
import androidx.lifecycle.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel() {


    private var _profile = MutableLiveData<List<Profiles>>()
    val profile: MutableLiveData<List<Profiles>> get() = _profile


    private var _bio = MutableLiveData<String>()
    val bio: MutableLiveData<String> get() = _bio

    private var _username = MutableLiveData<String>()
    val username: MutableLiveData<String> get() = _username

    private var _proImage = MutableStateFlow<String>("")
    val proImage: LiveData<String> get() = _proImage.asLiveData()


    init {
        getProfilrByUserId()
    }


    fun currentUserID(): String {
        return Firebase.auth.currentUser!!.uid
    }


    fun getProfilrByUserId() {
        val userId = currentUserID()
        viewModelScope.launch {
            Firebase.firestore.collection("profiles").whereEqualTo("userid", userId)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            _username.value = documentSnapshot.data?.get("username").toString()
                            _bio.value = documentSnapshot.data?.get("bio").toString()
                            _proImage.update { documentSnapshot.data?.get("imageUrl").toString() }
                        }
                    }
                })
        }

    }


    class DeleteFirebase {

        fun delete() {
            val db = FirebaseFirestore.getInstance()
            Firebase.auth
            Log.e("TAG", "save: start")

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
}