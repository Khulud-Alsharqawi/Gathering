package com.khulud.gathering.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gathering.R
import com.khulud.gathering.model.EventsList

class EventsAdapter(private val EventList: List<EventsList>, private val context: Context) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageItem)
        val textView: TextView = view.findViewById(R.id.eventName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.events_list, parent, false)
        )

    }

    override fun onBindViewHolder(holder: EventsAdapter.ViewHolder, position: Int) {
        val item = EventList[position]
        val images = item.eventImage
        val imgUri = images.toUri().buildUpon().build()
        Glide.with(holder.imageView)
            .load(imgUri)
            .into(holder.imageView)
        holder.textView.text = item.eventName
    }

    override fun getItemCount(): Int = EventList.size
}

