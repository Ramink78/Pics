package com.unsplash.retrofit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.DataItem
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.collections.CollectionsData


class CollectionsAdapter() : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {
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
        Picasso.get().load(data[position].coverPhotos.urls.small).placeholder(R.color.CardFooterColor).into(holder.image);
        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
        }
        holder.itemView.setOnClickListener{
            onPhotoClickListener?.onClick(position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText = itemView.findViewById<TextView>(R.id.cPrimaryText)
        val seconderyText = itemView.findViewById<TextView>(R.id.cSeconderyText)
        val image = itemView.findViewById<ShapeableImageView>(R.id.cPic)

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
    fun setOnPhotoClickListener(listener: OnPhotoClickListener){
        onPhotoClickListener=listener
    }
    fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }


}