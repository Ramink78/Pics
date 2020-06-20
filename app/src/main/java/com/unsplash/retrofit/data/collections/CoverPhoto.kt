package com.unsplash.retrofit.data.collections

import com.google.gson.annotations.SerializedName

data class CoverPhoto(
    @SerializedName("id")
    var id: String,
    @SerializedName("width")
    var width: Int,
    @SerializedName("height")
    var height: Int,
    @SerializedName("urls")
    var urls: CollectionsCoverUrls,
    @SerializedName("color")
    var color: String
){

}