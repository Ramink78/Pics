package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView


class HomeAdapters(val context: Context) :
    PagedListAdapter<Photo, HomeAdapters.ViewHolder>(PhotoDiffCallBack()) {
    private var onPhotoClickListener: OnPhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
        )

    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.image.apply {
            this.transitionName = item?.id
            aspectRatio =
                item!!.height.toDouble() / item.width.toDouble()
            Glide.with(context).load(item.urls.regular)
                .thumbnail(
                    Glide.with(context).load(item.urls.thumb)
                )
                .placeholder(ColorDrawable(Color.parseColor(item.color)))
                .transition(DrawableTransitionOptions.withCrossFade()).into(this)
        }

        holder.image.setOnClickListener {
            onPhotoClickListener?.onClick(item?.id, position, it, item)
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: AspectRatioImageView = itemView.findViewById(R.id.cePic)

    }

    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {
        onPhotoClickListener = listener


    }


    class PhotoDiffCallBack : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == (newItem)
        }

    }


}