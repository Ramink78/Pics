package com.unsplash.retrofit.data.random


import com.google.gson.annotations.SerializedName

data class Explore(
    @SerializedName("alt_description")
    var altDescription: String,
    @SerializedName("categories")
    var categories: List<Any>,
    @SerializedName("color")
    var color: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("current_user_collections")
    var currentUserCollections: List<Any>,
    @SerializedName("description")
    var description: String,
    @SerializedName("downloads")
    var downloads: Int,
    @SerializedName("exif")
    var exif: Exif,
    @SerializedName("height")
    var height: Int,
    @SerializedName("id")
    var id: String,
    @SerializedName("liked_by_user")
    var likedByUser: Boolean,
    @SerializedName("likes")
    var likes: Int,
    @SerializedName("links")
    var links: Links,
    @SerializedName("location")
    var location: Location,
    @SerializedName("promoted_at")
    var promotedAt: String,
    @SerializedName("sponsorship")
    var sponsorship: Any,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("urls")
    var urls: Urls,
    @SerializedName("user")
    var user: User,
    @SerializedName("views")
    var views: Int,
    @SerializedName("width")
    var width: Int
)