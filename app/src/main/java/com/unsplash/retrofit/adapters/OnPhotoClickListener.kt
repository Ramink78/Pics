package com.unsplash.retrofit.adapters

import android.view.View

interface OnPhotoClickListener {
    fun onClick(id: String?, position: Int, view: View)

}