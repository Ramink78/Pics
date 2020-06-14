package com.unsplash.retrofit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.random.Explore


class ExploreAdapter() : RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {
    private val data: ArrayList<Explore> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.explore_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(data[position].urls.thumb)
            .placeholder(R.color.CardFooterColor).into(holder.image);
        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ShapeableImageView>(R.id.ePic)

    }

    fun addItems(items: ArrayList<Explore>) {
        if (items.isEmpty()) return
        val startIndex = data.size
        data.addAll(items)
        notifyItemRangeInserted(startIndex, items.size)
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        onLoadMoreListener = listener
    }

    fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }


}