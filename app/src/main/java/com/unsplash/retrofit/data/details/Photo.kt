package com.unsplash.retrofit.data.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unsplash.retrofit.Urls
import com.unsplash.retrofit.User
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class Photo(
    @SerializedName("user")
    val user: User, @SerializedName("width")
    val width: Int, @SerializedName("height")
    val height: Int, @SerializedName("location")
    val location: Location, @SerializedName("exif")
    val exif: Exifs, @SerializedName("color")
    val color: String, @SerializedName("id")
    val id: String, @SerializedName("created_at")
    val createdAt: String, @SerializedName("urls")
    val urls: Urls, var collapsed: Boolean = false
) : Serializable
