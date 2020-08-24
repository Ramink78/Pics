package com.unsplash.retrofit.data.statistics

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PhotoStatistics(
    val id: String,
    val downloads: Downloads,
    val views: Views,
) : Parcelable
@Parcelize
@JsonClass(generateAdapter = true)
data class Downloads(
    val total: Int,
) : Parcelable
@Parcelize
@JsonClass(generateAdapter = true)
data class Views(
    val total: Int,
) : Parcelable