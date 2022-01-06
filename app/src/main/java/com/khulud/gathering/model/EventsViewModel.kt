package com.khulud.gathering.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull
import com.google.android.gms.common.api.Api

import com.google.android.gms.tasks.OnCompleteListener

class EventsViewModel : ViewModel() {

    private var _event = MutableLiveData<List<EventsList>>()
    val event: MutableLiveData<List<EventsList>> get() = _event

    private var _bookmarkItem = MutableLiveData<List<BookmarkEventsList>>()
    val bookmarkItem: MutableLiveData<List<BookmarkEventsList>> get() = _bookmarkItem

//    private var _selected = MutableLiveData<Model>()
//    val selected: LiveData<Model> get() = _selected

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


    init {
        getEvents()
        //setSelectedEventsList()
    }

    private fun getEvents() {
        event.value = listOf()
    }

//    fun setSelectedEventsList() {
//        _selected.value = selected.value
//    }

    companion object {
        val BookmarkEventsList = BookmarkEvents()
    }

}

