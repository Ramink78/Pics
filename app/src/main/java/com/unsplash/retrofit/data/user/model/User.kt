package com.unsplash.retrofit.data.user.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.unsplash.retrofit.data.photo.model.Photo
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    val id: String,
    val updated_at: String?,
    val username: String?,
    val name: String?,
    val first_name: String?,
    val last_name: String?,
    val instagram_username: String?,
    val twitter_username: String?,
    val portfolio_url: String?,
    val bio: String?,
    val location: String?,
    val total_likes: Int?,
    val total_photos: Int?,
    val total_collections: Int?,
    val followed_by_user: Boolean?,
    val followers_count: Int?,
    val following_count: Int?,
    val downloads: Int?,
    val profile_image: ProfileImage?,
    val photos: List<Photo>?
) : Parcelable
@Parcelize
@JsonClass(generateAdapter = true)
data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String
) : Parcelable
