package com.unsplash.retrofit.data.random


import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double
)