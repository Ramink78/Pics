package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.details.Photo
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView


class HomeAdapters(val context: Context) : RecyclerView.Adapter<HomeAdapters.ViewHolder>() {
    private val data: ArrayList<Photo> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var onPhotoClickListener: OnPhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.apply {
           this.transitionName=data[position].id
            aspectRatio =data[position].height!!.toDouble() / data[position].width!!.toDouble()
            Glide.with(context).load(data[position].urls.regular)
                .placeholder(ColorDrawable(Color.parseColor(data[position].color)))
                .transition(DrawableTransitionOptions.withCrossFade()).into(this)
        }

        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()

        }

        holder.image.setOnClickListener {
            onPhotoClickListener?.onClick(data[position].id, position,it,data[position])
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: AspectRatioImageView = itemView.findViewById(R.id.cePic)

    }

    fun addItems(items: ArrayList<Photo>) {
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



}