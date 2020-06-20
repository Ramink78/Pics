package com.unsplash.retrofit.data.details

import com.google.gson.annotations.SerializedName

class Location(
    @SerializedName("title")
    val title: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String
)