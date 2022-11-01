package com.khulud.gathering.model


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class EventsViewModel : ViewModel() {

    private var _event = MutableLiveData<List<EventsList>>()
    val event: MutableLiveData<List<EventsList>> get() = _event

    private var _bookmarkItem = MutableLiveData<List<EventsList>>()

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




    init {
        getEvents()
    }

    private fun getEvents() {
        event.value = listOf()
    }

}

