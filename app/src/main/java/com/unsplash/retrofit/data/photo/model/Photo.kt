package com.unsplash.retrofit.data.photo.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.unsplash.retrofit.data.statistics.PhotoStatistics
import com.unsplash.retrofit.data.user.model.User
import kotlinx.android.parcel.Parcelize
@Parcelize
@JsonClass(generateAdapter = true)
data class Photo(
    val id: String,
    val created_at: String?,
    val updated_at: String?,
    val width: Int?,
    val height: Int?,
    val color: String? = "#E0E0E0",
    val views: Int?,
    val downloads: Int?,
    val likes: Int?,
    var liked_by_user: Boolean?,
    val description: String?,
    val alt_description: String?,
    val exif: Exif?,
    val location: Location?,
    val urls: Urls,
    val user: User?,
    val statistics: PhotoStatistics?
):Parcelable
@Parcelize
@JsonClass(generateAdapter = true)
data class Exif(
    val make:String?,
    val model: String?,
    val exposure_time: String?,
    val aperture:String?,
    val focal_length:String,
    val iso :Int?
):Parcelable
@Parcelize
@JsonClass(generateAdapter = true)
data class  Location(
    val city:String?,
    val country:String?,
):Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Urls(
    val raw:String?,
    val full:String?,
    val regular:String?,
    val thumb:String,
    val small:String?
):Parcelable
