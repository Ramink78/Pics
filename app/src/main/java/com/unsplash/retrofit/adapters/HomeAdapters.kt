package com.unsplash.retrofit.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.DataItem
import com.unsplash.retrofit.R


class HomeAdapters : RecyclerView.Adapter<HomeAdapters.ViewHolder>() {
    private val data: ArrayList<DataItem> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.primaryText.text = data[position].user.firstName
        holder.seconderyText.text = "@${data[position].user.username}"
        Log.i("Links", data[position].urls.small)
        Log.i("Links", " position is: $position")
        Picasso.get().load(data[position].urls.small).placeholder(R.color.CardFooterColor)
            .into(holder.image);
        Picasso.get().load(data[position].user.profileImage.medium).fit().into(holder.profile);
        var difference = data.size - position
        //    Log.i("difrence", position.toString())
        if (position == data.size - 5) {
            onLoadMoreListener?.onLoadMoreData()
            //    this.notifyItemChanged(data.indexOf(data[position]))

        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText = itemView.findViewById<TextView>(R.id.cetPrimary)
        val seconderyText = itemView.findViewById<TextView>(R.id.cetSecondery)
        val image = itemView.findViewById<ShapeableImageView>(R.id.cePic)
        val profile = itemView.findViewById<ShapeableImageView>(R.id.ceProfile)

    }

    fun addItems(items: ArrayList<DataItem>) {
        if (items.isEmpty()) return
        val startIndex = data.size
        data.addAll(items)
        notifyItemRangeInserted(startIndex, items.size)
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        onLoadMoreListener = listener
    }

    fun updateList(newList: ArrayList<DataItem>) {
        val diffResult = DiffUtil.calculateDiff(MyCallback(this.data, newList))
        this.data.addAll(newList);
        diffResult.dispatchUpdatesTo(this)
    }

    fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }


}