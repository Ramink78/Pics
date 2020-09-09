package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.collections.model.Collection
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView
import java.util.*


class CollectionsAdapter2(val context: Context) : RecyclerView.Adapter<CollectionsAdapter2.ViewHolder>() {
    private val data: ArrayList<Collection> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var onPhotoClickListener: OnPhotoClickListener? = null
    private val differ=AsyncPagedListDiffer(this,CollectionDiffCallback())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.collections_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return differ.itemCount
    }
    fun submitList(collections: PagedList<Collection>) {
        differ.submitList(collections)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collection=differ.getItem(position)
        holder.primaryText.text = collection!!.title
        holder.seconderyText.text = "${collection.totalPhotos} Photos"

        holder.image.setAspectRatio(
            collection.coverPhotos.width,
            collection.coverPhotos.height
        )
        holder.image.setBackgroundColor(Color.parseColor(collection.coverPhotos.color))

        Glide.with(context).load(collection.coverPhotos.urls.regular)
            .placeholder(ColorDrawable(Color.parseColor(collection.coverPhotos.color)))
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.image)

        Glide.with(context).load(collection.user.profileImage.medium)
            .placeholder(ColorDrawable(Color.parseColor(collection.coverPhotos.color)))
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.profile)

        holder.itemView.setOnClickListener {
            onPhotoClickListener?.onClick(collection.id, position,it,collection.coverPhotos)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText = itemView.findViewById<TextView>(R.id.cPrimaryText)
        val seconderyText = itemView.findViewById<TextView>(R.id.cSeconderyText)
        val image = itemView.findViewById<AspectRatioImageView>(R.id.cPic)
        val profile = itemView.findViewById<ShapeableImageView>(R.id.c_profile)

    }

    fun addItems(items: ArrayList<Collection>) {
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
    class CollectionDiffCallback:DiffUtil.ItemCallback<Collection>(){
        override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem==newItem
        }

    }


}