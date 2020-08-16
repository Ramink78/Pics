package com.unsplash.retrofit.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.DataItem
import com.unsplash.retrofit.R
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView
import java.lang.Exception


class HomeAdapters : RecyclerView.Adapter<HomeAdapters.ViewHolder>() {
    private val data: ArrayList<DataItem> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var onPhotoClickListener: OnPhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        Log.i("Link photo",data[22].urls.small)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //    holder.image.setAspectRatio(data[position].width, data[position].height)
        holder.image.setBackgroundColor(Color.parseColor(data[position].color))
        Picasso.get().load(data[position].urls.regular)
            .into(holder.image)

        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
        }

        holder.itemView.setOnClickListener {
            onPhotoClickListener?.onClick(null, position)
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ShapeableImageView>(R.id.cePic)

    }

    fun addItems(items: ArrayList<DataItem>) {
        if (items.isEmpty()) return
        val startIndex = data.size
        data.addAll(items)
        notifyItemRangeInserted(startIndex, items.size)
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        onLoadMoreListener = listener
    }

    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {
        onPhotoClickListener = listener


    }

    fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }

    private fun AspectRatioImageView.setAspectRatio(width: Int?, height: Int?) {
        if (width != null && height != null) {
            aspectRatio = height.toDouble() / width.toDouble()
        }
    }


}