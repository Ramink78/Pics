package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.network.NetworkState
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView


class HomeAdapters(val context: Context) :
    RecyclerView.Adapter<HomeAdapters.PhotoVH>() {
    private var onPhotoClickListener: OnPhotoClickListener? = null
    val PROGRESS_TYPE = 0
    val PHOTO_TYPE = 1
    var networkState: NetworkState? = null

    private val differ = AsyncPagedListDiffer(this, PhotoDiffCallBack())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        return PhotoVH(
            LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        val photo = differ.getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }


    }


    inner class PhotoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: AspectRatioImageView = itemView.findViewById(R.id.cePic)
        fun bind(photo: Photo) {
            image.apply {
                this.transitionName = photo.id
                aspectRatio =
                    photo.height.toDouble() / photo.width.toDouble()
                Glide.with(context).load(photo.urls.regular)
                    .thumbnail(
                        Glide.with(context).load(photo.urls.thumb)
                    )
                    .placeholder(ColorDrawable(Color.parseColor(photo.color)))
                    .transition(DrawableTransitionOptions.withCrossFade()).into(this)
                setOnClickListener {
                    onPhotoClickListener?.onClick(photo.id, adapterPosition, it, photo)

                }
            }
        }

    }


    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {

      //  onPhotoClickListener = listener

    }


    class PhotoDiffCallBack : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }

    fun submitList(photos: PagedList<Photo>) {
        differ.submitList(photos)
    }

    override fun getItemCount(): Int {
        return differ.itemCount
    }


}