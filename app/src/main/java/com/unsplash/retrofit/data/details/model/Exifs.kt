package com.unsplash.retrofit.data.details.model

import com.google.gson.annotations.SerializedName

class Exifs(
    @SerializedName("make")
    val make: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("exposure_time")
    val exposureTime: String?,
    @SerializedName("aperture")
    val aperture: String?,
    @SerializedName("focal_length")
    val focalLength: String?,
    @SerializedName("iso")
    val iso: String
)
