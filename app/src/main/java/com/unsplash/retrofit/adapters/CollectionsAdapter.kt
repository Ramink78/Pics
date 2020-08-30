package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.collections.CollectionsData
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView
import java.util.*


class CollectionsAdapter(val context: Context) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {
    private val data: ArrayList<CollectionsData> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var onPhotoClickListener: OnPhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.collections_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.primaryText.text = data[position].title
        holder.seconderyText.text = "${data[position].totalPhotos} Photos"

        holder.image.setAspectRatio(
            data[position].coverPhotos.width,
            data[position].coverPhotos.height
        )
        holder.image.setBackgroundColor(Color.parseColor(data[position].coverPhotos.color))

        Glide.with(context).load(data[position].coverPhotos.urls.regular)
            .placeholder(ColorDrawable(Color.parseColor(data[position].coverPhotos.color)))
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.image)

        Glide.with(context).load(data[position].user.profileImage.medium)
            .placeholder(ColorDrawable(Color.parseColor(data[position].coverPhotos.color)))
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.profile)



        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
        }
        holder.itemView.setOnClickListener {
            onPhotoClickListener?.onClick(data[position].id, position,it,data[position].coverPhotos)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText = itemView.findViewById<TextView>(R.id.cPrimaryText)
        val seconderyText = itemView.findViewById<TextView>(R.id.cSeconderyText)
        val image = itemView.findViewById<AspectRatioImageView>(R.id.cPic)
        val profile = itemView.findViewById<ShapeableImageView>(R.id.c_profile)

    }

    fun addItems(items: ArrayList<CollectionsData>) {
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
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}