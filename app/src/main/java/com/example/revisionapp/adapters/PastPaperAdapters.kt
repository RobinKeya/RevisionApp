package com.example.revisionapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.revisionapp.R
import com.example.revisionapp.models.Images

class PastPaperAdapters(private val context: Context): RecyclerView.Adapter<PastPaperAdapters.ViewHolder>() {
    private var images: MutableList<Images> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.image_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //glide
    }

    override fun getItemCount(): Int {
        return images.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.image)
    }
}