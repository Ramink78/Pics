package com.unsplash.retrofit.adapters

import android.view.View
import androidx.annotation.Nullable
import com.unsplash.retrofit.data.details.Photo

interface OnPhotoClickListener {
    fun onClick(id: String?, position: Int, view: View,  photo: Photo?)

}