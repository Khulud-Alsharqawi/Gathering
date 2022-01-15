package com.khulud.gathering.model

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull
import com.google.android.gms.common.api.Api
import com.google.android.gms.tasks.Continuation

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class EventsViewModel : ViewModel() {

    private var _event = MutableLiveData<List<EventsList>>()
    val event: MutableLiveData<List<EventsList>> get() = _event

    private var _bookmarkItem = MutableLiveData<List<BookmarkEventsList>>()
    val bookmarkItem: MutableLiveData<List<BookmarkEventsList>> get() = _bookmarkItem

    private var _eventName = MutableLiveData<String>()
    val eventName: MutableLiveData<String> get() = _eventName

    private var _imageUrl = MutableLiveData<String>()
    val imageUrl: MutableLiveData<String> get() = _imageUrl

    private var _eventsLocation = MutableLiveData<String>()
    val eventsLocation: MutableLiveData<String> get() = _eventsLocation

    private var _eventDescription = MutableLiveData<String>()
    val eventDescription: MutableLiveData<String> get() = _eventDescription


    private var _price = MutableLiveData<String>()
    val price: MutableLiveData<String> get() = _price

    private var _eventDate = MutableLiveData<String>()
    val eventDate: MutableLiveData<String> get() = _eventDate

    private var _userProfile = MutableLiveData<String>()
    val userProfile: MutableLiveData<String> get() = _userProfile

    private var storageReference: StorageReference? = null


    init {
        getEvents()
    }

    private fun getEvents() {
        event.value = listOf()
    }

/*
!*----------------------------------------
    // region upload image to firebase

    private fun uploadImage() {

        storageReference = FirebaseStorage.getInstance().reference

        Log.e("TAG", "uploadImage: $imageUrl", )
        if (imageUrl != null) {
            val ref = storageReference?.child(
                "UserProfileImage/"
                        + UUID.randomUUID().toString()
            )
        val uploadTask = ref?.putFile(imageUrl!!)

            val urlTask = uploadTask?.continueWithTask(
                Continuation<UploadTask.TaskSnapshot, Task<Uri>>
                { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener {

                    task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())
                    reImg(downloadUri.toString())
                } else {
                    // Handle failures
                }
            }?.addOnFailureListener {

            }
        } else {
            Toast.makeText(this.requireContext(), "Please Upload an Image", Toast.LENGTH_SHORT)
                .show()
        }
    }
    // endregion
    !*--------------------------------------
 */

    fun reImg  (s : String) : String{


        return s
    }

}

