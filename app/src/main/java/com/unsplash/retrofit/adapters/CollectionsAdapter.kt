package com.unsplash.retrofit.adapters

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
        Picasso.get().load(data[position].coverPhotos.urls.small).into(holder.image);
        //  Log.i("Mytag","first name is: ${data[position].id}")
        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
            //    this.notifyItemChanged(data.indexOf(data[position]))

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText = itemView.findViewById<TextView>(R.id.cctPrimary)
        val seconderyText = itemView.findViewById<TextView>(R.id.cctSecondery)
        val image = itemView.findViewById<ShapeableImageView>(R.id.ccPic)

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
    fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }

    /*   fun addToAdapter(data:ArrayList<Data>){
           for (i in 0 until data.size)
               this.data.addAll()


       }*/

}