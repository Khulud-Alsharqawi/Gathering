package com.khulud.gathering.utility.ui

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khulud.gathering.adapter.EventsAdapter
import com.khulud.gathering.model.EventsList

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imageUrl: String?){
        this.bindImage(imageUrl = imageUrl)
    }


@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<EventsList>?) {
    val adapter = this.adapter as EventsAdapter
    setOf(adapter)
}