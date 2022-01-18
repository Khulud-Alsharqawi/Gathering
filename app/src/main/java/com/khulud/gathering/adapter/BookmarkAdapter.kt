package com.khulud.gathering.adapter

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
import com.khulud.gathering.fragmnets.BookMarkFragmentDirections
import com.khulud.gathering.model.EventsList

class BookmarkAdapter(private val bookmarkEveList: ArrayList<EventsList>) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.imageItem)
        val textView: TextView = itemView.findViewById(R.id.eventName)
        val btnDetails: Button = itemView.findViewById(R.id.btnDetails)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.events_list,
            parent, false
        )
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {
        val item = bookmarkEveList[position]

        val images = item.eventImage
        val imgUri = images.toUri().buildUpon().build()
        Glide.with(holder.imageView)
            .load(imgUri)
            .into(holder.imageView)
        holder.textView.text = item.eventName
        holder.btnDetails.setOnClickListener {

            val action = BookMarkFragmentDirections.actionBookMarkFragmentToDetailsFragment(
                item.eventName ?: ""
            )
            holder.itemView.findNavController().navigate(action)


        }
    }

    override fun getItemCount(): Int = bookmarkEveList.size
}

