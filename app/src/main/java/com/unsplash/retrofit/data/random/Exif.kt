package com.unsplash.retrofit.data.random


import com.google.gson.annotations.SerializedName

data class Exif(
    @SerializedName("aperture")
    var aperture: Any,
    @SerializedName("exposure_time")
    var exposureTime: Any,
    @SerializedName("focal_length")
    var focalLength: Any,
    @SerializedName("iso")
    var iso: Any,
    @SerializedName("make")
    var make: Any,
    @SerializedName("model")
    var model: Any
)