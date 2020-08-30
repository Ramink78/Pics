package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.databinding.CDetailsBinding
import com.unsplash.retrofit.databinding.HomeItemBinding
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView
import java.util.*


class CollectionPhotosAdapter(val context: Context) :
    RecyclerView.Adapter<CollectionPhotosAdapter.ViewHolder>() {
    private val data: ArrayList<Photo> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    lateinit var photoSelectedListener: PhotoSelectedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)

        return ViewHolder(HomeItemBinding.inflate(inflater,parent,false))

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

  inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      private lateinit var binding:HomeItemBinding
        val image: AspectRatioImageView = itemView.findViewById(R.id.cePic)

        constructor(binding: HomeItemBinding) : this(binding.root){
            this.binding=binding


        }
        fun bind(photo: Photo){
            image.apply {
                transitionName=photo.id
                aspectRatio =photo.height.toDouble() / photo.width.toDouble()
            }
            if (adapterPosition == data.size - 5) {
                onLoadMoreListener?.onLoadMoreData()
            }
            image.setOnClickListener {
                Log.i("onclick", "clicked pn photo ")
                photoSelectedListener.onPhotoSelected(photo,image)
            }
            Glide.with(context).load(photo.urls.regular)
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

    interface PhotoSelectedListener {
        fun onPhotoSelected(photo: Photo, imageView: AspectRatioImageView)
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }






}