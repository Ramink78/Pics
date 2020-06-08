package com.unsplash.retrofit


import com.google.gson.annotations.SerializedName

data class ProfileImageX(
    @SerializedName("large")
    var large: String,
    @SerializedName("medium")
    var medium: String,
    @SerializedName("small")
    var small: String


)