package com.unsplash.retrofit.adapters

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.unsplash.retrofit.DataItem


class MyCallback():DiffUtil.Callback(){
    lateinit var oldData:ArrayList<DataItem>

    lateinit var newData:ArrayList<DataItem>
    constructor(oldData: ArrayList<DataItem>,newData: ArrayList<DataItem>) : this() {
        this.oldData=oldData
        this.newData=newData

    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return  oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id.equals(newData[newItemPosition].id);
    }



}