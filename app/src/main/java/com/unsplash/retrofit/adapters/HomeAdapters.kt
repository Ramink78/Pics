package com.unsplash.retrofit.adapters

import android.content.Context
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


class HomeAdapters(): RecyclerView.Adapter<HomeAdapters.ViewHolder>() {
    private var mInflater: LayoutInflater?=null
    lateinit var data:ArrayList<DataItem>
    lateinit var context: Context


    constructor (context: Context, array: ArrayList<DataItem>) : this(){
        this.mInflater = LayoutInflater.from(context);
        this.context=context
        this.data=array
    }
    companion object {
      private  lateinit var endOfListListen: endOfListListener
        fun onEndListListener(listener: endOfListListener) {
            this.endOfListListen = listener

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= mInflater!!.inflate(R.layout.explore_item2,parent,false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return  data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.primaryText.text=data[position].user.firstName
        holder.seconderyText.text="@${data[position].user.username}"
      Picasso.get().load(data[position].urls.small).placeholder(R.color.BottomNavColor).into(holder.image);
        Picasso.get().load(data[position].user.profileImage.medium).fit().into(holder.profile);
        var difference=data.size-position
    //    Log.i("difrence", position.toString())
        if (position ==data.size-5){
                endOfListListen.onEndList()
            //    this.notifyItemChanged(data.indexOf(data[position]))

        }

    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText=itemView.findViewById<TextView>(R.id.cetPrimary)
        val seconderyText=itemView.findViewById<TextView>(R.id.cetSecondery)
        val image=itemView.findViewById<ShapeableImageView>(R.id.cePic)
        val profile=itemView.findViewById<ShapeableImageView>(R.id.ceProfile)

    }


 /*   fun addToAdapter(data:ArrayList<Data>){
        for (i in 0 until data.size)
            this.data.addAll()


    }*/
  fun updateList(newList: ArrayList<DataItem>) {
     val diffResult = DiffUtil.calculateDiff(MyCallback(this.data, newList))
     this.data.addAll(newList);
     diffResult.dispatchUpdatesTo(this)
 }


}