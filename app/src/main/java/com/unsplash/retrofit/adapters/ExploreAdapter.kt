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
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.details.Photo
import com.unsplash.retrofit.data.random.Explore
import com.unsplash.retrofit.data.searchdata.Result
import com.unsplash.retrofit.ui.explore.ExploreFragment


class ExploreAdapter(val context: Context) : RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {
    private var onPhotoClickListener: OnPhotoClickListener? = null
    private val data: ArrayList<Photo> = arrayListOf()
    private val dataSearch: ArrayList<Result> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var exploreFragment: ExploreFragment? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.explore_item, parent, false)
        exploreFragment = ExploreFragment()

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.transitionName=data[position].id
        Glide.with(context).load(data[position].urls.thumb)
            .placeholder(ColorDrawable(Color.parseColor(data[position].color)))
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.image)



        holder.image.setOnClickListener {
            onPhotoClickListener?.onClick(data[position].id, position,it,data[position])
        }
        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.ePic)

    }

    fun addItems(items: ArrayList<Photo>) {
        if (items.isEmpty()) return
        val startIndex = data.size
        data.addAll(items)
        notifyItemRangeInserted(startIndex, items.size)
    }

    fun addSearchItems(items: ArrayList<Result>) {
        if (items.isEmpty()) return
        data.clear()
        dataSearch.addAll(items)
        notifyItemRangeInserted(0, items.size)
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

