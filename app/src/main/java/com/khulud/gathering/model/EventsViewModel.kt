package com.khulud.gathering.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


enum class EventsDataStatus { LOADING, ERROR, DONE }


class EventsViewModel : ViewModel() {
    private var _event = MutableLiveData<List<EventsList>>()
    val event: MutableLiveData<List<EventsList>> get() = _event
    private var _status = MutableLiveData<EventsDataStatus>()
    val status: LiveData<EventsDataStatus> get() = _status
    private var _selected = MutableLiveData<EventsDataStatus>()
    val selected: LiveData<EventsDataStatus> get() = _selected

  //  var tempEventList = mutableListOf<EventsList>()

    private var _eventName = MutableLiveData<String>()
    val eventName: MutableLiveData<String> get() = _eventName
    private var _imageUrl = MutableLiveData<List<EventsList>>()
    val imageUrl: MutableLiveData<List<EventsList>> get() = _imageUrl
    private var _eventsLocation = MutableLiveData<String>()
    val eventsLocation: MutableLiveData<String> get() = _eventsLocation
    private var _eventDescription = MutableLiveData<String>()
    val eventDescription: MutableLiveData<String> get() = _eventDescription
    private var _backdropPath = MutableLiveData<String>()
  //  val backdropPath: MutableLiveData<String> get() = _backdropPath


    init {
        //getEvents()
        setSelectedEventsList()
       // getEventsById(eventName.toString())
    }

//    private fun getEvents() {
//        viewModelScope.launch {
//            _status.value = EventsDataStatus.LOADING
//            try {
//                _event.value= FirebaseFirestore.getInfo().results
//                _status.value = EventsDataStatus.DONE
//            } catch (e: Exception) {
//                Log.d("viewModelScope", "error --> $e")
//                _status.value = EventsDataStatus.ERROR
//                event.value = listOf()
//            }
//
//
//        }
//    }

    fun setSelectedEventsList() {
        _selected.value = selected.value
    }

//    fun getEventsById(eventName: String): List<EventsList>? {
//        if (eventName == null) {
//            _event.value = tempEventList
//        } else {
//            viewModelScope.launch {
//                _status.value = EventsDataStatus.LOADING
//                try {
//                    _event.value = getEventsById("")
//                    _status.value = EventsDataStatus.DONE
//                } catch (e: java.lang.Exception) {
//                    Log.d("viewModelScope", "error --> $e")
//                    _status.value = EventsDataStatus.ERROR
//                    event.value = listOf()
//                }
//            }
//        }
//
//
//}

}