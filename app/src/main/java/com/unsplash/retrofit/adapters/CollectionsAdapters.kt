package com.unsplash.retrofit.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.Data
import com.unsplash.retrofit.DataItem
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.collections.Collections
import com.unsplash.retrofit.data.collections.CollectionsData


class CollectionsAdapters(): RecyclerView.Adapter<CollectionsAdapters.ViewHolder>() {
    private var mInflater: LayoutInflater?=null
    lateinit var data:ArrayList<CollectionsData>

    constructor (context: Context, array: ArrayList<CollectionsData>) : this(){
        this.mInflater = LayoutInflater.from(context);
        this.data=array
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= mInflater!!.inflate(R.layout.collections_item,parent,false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.primaryText.text=data[position].title
        holder.seconderyText.text="${data[position].totalPhotos} Photos"
        Picasso.get().load(data[position].coverPhotos.urls.small).into(holder.image);
      //  Log.i("Mytag","first name is: ${data[position].id}")
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText=itemView.findViewById<TextView>(R.id.cctPrimary)
        val seconderyText=itemView.findViewById<TextView>(R.id.cctSecondery)
        val image=itemView.findViewById<ShapeableImageView>(R.id.ccPic)

    }
 /*   fun addToAdapter(data:ArrayList<Data>){
        for (i in 0 until data.size)
            this.data.addAll()


    }*/

}