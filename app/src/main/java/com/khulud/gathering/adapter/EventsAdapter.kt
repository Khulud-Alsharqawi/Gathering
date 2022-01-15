package com.khulud.gathering.adapter

import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gathering.R
import com.khulud.gathering.fragmnets.HomeFragmentDirections
import com.khulud.gathering.model.EventsList

class EventsAdapter(private val EveList: ArrayList<EventsList>) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.imageItem)
        val textView: TextView = itemView.findViewById(R.id.eventName)
        val btnDetails: Button = itemView.findViewById(R.id.btnDetails)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.events_list,
            parent, false
        )
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: EventsAdapter.ViewHolder, position: Int) {
        val item = EveList[position]

        val images = item.eventImage
        val imgUri = images.toUri().buildUpon().build()
        Glide.with(holder.imageView)
            .load(imgUri)
            .into(holder.imageView)
        holder.textView.text = item.eventName
        holder.textView
        holder.btnDetails.setOnClickListener {


            val actionDetails =
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.eventName ?: "")
            holder.itemView.findNavController().navigate(actionDetails)


        }

    }

    override fun getItemCount(): Int = EveList.size
}

