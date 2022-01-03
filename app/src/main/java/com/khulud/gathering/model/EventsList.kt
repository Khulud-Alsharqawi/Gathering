package com.khulud.gathering.model

import com.google.firebase.firestore.Exclude

import com.google.firebase.firestore.IgnoreExtraProperties




data class EventsList(val eventImage: String= " ",
                      val eventName: String= " ",
                      val eventInfo: String =" ",
                      val eventLocation: String =" ")


@IgnoreExtraProperties
class Model {
    @Exclude
    var id: String? = null
    fun <T : Model?> withId(id: String): T {
        this.id = id
        return this as T
    }
}
