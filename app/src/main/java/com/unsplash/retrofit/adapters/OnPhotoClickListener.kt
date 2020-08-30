package com.unsplash.retrofit.adapters

import android.view.View
import com.unsplash.retrofit.data.details.model.Photo

interface OnPhotoClickListener {
    fun onClick(id: String?, position: Int, view: View, photo: Photo?)

}