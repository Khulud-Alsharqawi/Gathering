package com.khulud.gathering.utility.ui

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gathering.R
import com.khulud.gathering.adapter.BookmarkAdapter
import com.khulud.gathering.adapter.EventsAdapter
import com.khulud.gathering.model.EventsList

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imageUrl: String?){
   var image= imageUrl?.toUri()?.buildUpon()?.build()
    Glide.with(this)
        .load(image)
        .placeholder(R.drawable.loading_animation)
        .into(this)
       // this.bindImage(imageUrl = imageUrl)
    }


@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<EventsList>?) {
    val adapter = this.adapter as EventsAdapter
    setOf(adapter)
}

@JvmName("bindRecyclerView1")
@BindingAdapter("EventsList")
fun bindRecyclerView(RecyclerView:RecyclerView,data: List<EventsList>?) {
    val adapter = RecyclerView.adapter as BookmarkAdapter
    setOf(adapter)
}