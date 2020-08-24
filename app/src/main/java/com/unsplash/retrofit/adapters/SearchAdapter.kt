package com.unsplash.retrofit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.searchdata.Result


class SearchAdapter : RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {
    private var onPhotoClickListener: OnPhotoClickListener? = null
    private val dataSearch: ArrayList<Result> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreAdapter.ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.explore_item, parent, false)


        return ExploreAdapter.ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return dataSearch.size
    }

    override fun onBindViewHolder(holder: ExploreAdapter.ViewHolder, position: Int) {
        holder.image.setBackgroundColor(Color.parseColor(dataSearch[position].color))
        Picasso.get().load(dataSearch[position].urls.small)
            .into(holder.image)
        holder.image.setOnClickListener {
            //onPhotoClickListener?.onClick(null, position,it,null)
        }
        if (position == dataSearch.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
        }
    }


    fun addItems(items: ArrayList<Result>) {
        if (items.isEmpty()) return
        val startIndex = dataSearch.size
        dataSearch.addAll(items)
        notifyItemRangeInserted(startIndex, items.size)
    }


    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        onLoadMoreListener = listener
    }

    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {
        onPhotoClickListener = listener
    }

    fun clear() {
        val size = dataSearch.size
        dataSearch.clear()
        notifyItemRangeRemoved(0, size)
    }


}

