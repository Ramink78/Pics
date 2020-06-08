package com.unsplash.retrofit.data.collections

import com.google.gson.annotations.SerializedName

data class CollectionsData(
    @SerializedName("id")
    var id:String,
    @SerializedName("title")
    var title:String ,
    @SerializedName("descriptions")
    var descriptions:String,
    @SerializedName("total_photos")
    var totalPhotos:Int,
    @SerializedName("cover_photo")
    var coverPhotos:CoverPhoto
)