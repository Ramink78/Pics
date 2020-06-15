package com.unsplash.retrofit.adapters

import android.graphics.Color
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
import java.lang.Exception


class HomeAdapters : RecyclerView.Adapter<HomeAdapters.ViewHolder>() {
    private val data: ArrayList<DataItem> = arrayListOf()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var onPhotoClickListener: OnPhotoClickListener? = null


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
        holder.image.setBackgroundColor(Color.parseColor(data[position].color))
        Picasso.get().load(data[position].urls.small)
            .into(holder.image)
        Picasso.get().load(data[position].user.profileImage.medium).into(holder.profile, object : com.squareup.picasso.Callback{
            override fun onSuccess() {
                if (position == data.size - 5) {
                    onLoadMoreListener?.onLoadMoreData()
                    //    this.notifyItemChanged(data.indexOf(data[position]))

                }
            }

            override fun onError(p0: Exception?) {
                TODO("Not yet implemented")
            }

        })
        var difference = data.size - position
        //    Log.i("difrence", position.toString())

        holder.itemView.setOnClickListener {
            onPhotoClickListener?.onClick(position)
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
    fun setOnPhotoClickListener(listener: OnPhotoClickListener){
        onPhotoClickListener=listener


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