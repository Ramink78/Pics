package com.unsplash.retrofit.data.photo

import com.unsplash.retrofit.data.photo.model.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoAPI {
    @GET("photos")
  suspend  fun getPhotos(
        @Query("page") page:Int?,
        @Query("per_page") per_page:Int?
    ):ArrayList<Photo>
    @GET("collections/{id}/photos")
   suspend fun getPhotosFromCollection(
        @Query("id") id:Int?,
        @Query("page") page:Int?,
        @Query("per_page") per_page: Int?
    ):List<Photo>
     @GET("photos/{id}")
   suspend  fun getPhoto(
         @Query("id") id:String?
     ):Photo


}