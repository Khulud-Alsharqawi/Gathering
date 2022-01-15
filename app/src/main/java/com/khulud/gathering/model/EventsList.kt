package com.khulud.gathering.model

data class EventsList(
    val eventImage: String = " ",
    val eventName: String = " ",
    val eventInfo: String = " ",
    val eventLocation: String = " ",
    val isBookMark: Boolean = false,
    val price: String = "",
    val eventDate: String = " "
)


data class BookmarkEventsList(
    val userUid: String = "",
    val eventsList: EventsList = EventsList()
)


data class Profiles(
    val userid: String = "",
    val username: String = "",
    val bio: String = "",
    val imageUrl: String = ""
)





