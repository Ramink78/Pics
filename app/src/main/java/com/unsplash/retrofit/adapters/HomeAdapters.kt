package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.DataItem
import com.unsplash.retrofit.R
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView
import java.lang.AssertionError
import java.lang.Exception


class HomeAdapters(val context: Context) : RecyclerView.Adapter<HomeAdapters.ViewHolder>() {
    private val data: ArrayList<DataItem> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var onPhotoClickListener: OnPhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setAspectRatio(data[position].width, data[position].height)
        holder.image.setBackgroundColor(Color.parseColor(data[position].color))
        Glide.with(context).load(data[position].urls.regular)
            .placeholder(ColorDrawable(Color.parseColor(data[position].color)))
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.image)
        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
        }

        holder.itemView.setOnClickListener {
            onPhotoClickListener?.onClick(null, position,it)
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<AspectRatioImageView>(R.id.cePic)

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