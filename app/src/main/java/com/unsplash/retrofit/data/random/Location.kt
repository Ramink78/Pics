package com.unsplash.retrofit.data.random


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    var city: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("position")
    var position: Position,
    @SerializedName("title")
    var title: String
)