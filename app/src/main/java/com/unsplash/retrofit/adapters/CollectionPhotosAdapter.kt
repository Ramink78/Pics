package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.collections.CollectionsData
import com.unsplash.retrofit.data.details.Photo
import com.unsplash.retrofit.databinding.HomeItemBinding
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView
import java.util.*


class CollectionPhotosAdapter(val context: Context) :
    RecyclerView.Adapter<CollectionPhotosAdapter.ViewHolder>() {
    private val data: ArrayList<Photo> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var onPhotoClickListener: OnPhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return ViewHolder(HomeItemBinding.inflate(inflater,parent,false))

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
     /*   holder.image.setAspectRatio(
            data[position].width,
            data[position].height
        )

        holder.image.setBackgroundColor(Color.parseColor(data[position].color))
        Glide.with(context).load(data[position].urls.regular)
            .placeholder(ColorDrawable(Color.parseColor(data[position].color)))
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.image)*/

        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
        }
        holder.itemView.setOnClickListener {
            onPhotoClickListener?.onClick(data[position].id, position,it)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      private lateinit var binding:HomeItemBinding
        val image = itemView.findViewById<AspectRatioImageView>(R.id.cePic)
        constructor(binding: HomeItemBinding) : this(binding.root){
            this.binding=binding

        }
        fun bind(photo:Photo){
            image.aspectRatio=photo.width.toDouble()/photo.height.toDouble()
//            image.setBackgroundColor(Color.parseColor(photo.color))
            Glide.with(binding.root.context).load(photo.urls.regular)
                .placeholder(ColorDrawable(Color.parseColor(photo.color)))
                .transition(DrawableTransitionOptions.withCrossFade()).into(image)
            binding.executePendingBindings()

        }


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

    private fun AspectRatioImageView.setAspectRatio(width: Int?, height: Int?) {
        if (width != null && height != null) {
            aspectRatio = height.toDouble() / width.toDouble()
        }
    }


}