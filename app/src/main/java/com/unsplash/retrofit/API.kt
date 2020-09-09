package com.unsplash.retrofit


import com.unsplash.retrofit.data.collections.model.CollectionPhotos
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.data.random.ExploreData
import com.unsplash.retrofit.data.searchdata.Search
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface API {
    @GET("search/photos/")
    fun getresult(@Query("client_id") key: String, @Query("query") quer: String): Call<Data>

    @GET("photos/")
    fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") count: Int
    ): Call<Data>

    @GET("collections/")
    fun getCollections(
        @Query("client_id") key: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int
    ): Call<Collections>

    @GET("photos/random")
    fun getRandom(
        @Query("client_id") key: String,
        @Query("count") count: Int
    ): Call<ExploreData>

    @GET("search/photos")
    fun photosFilter(
        @Query("client_id") key: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int,
        @Query("query") keyword: String
    ): Call<Search>

    @GET("photos/{id}")
    fun getDetails(
        @Path("id") id: String,
        @Query("client_id") apikey: String
    ): Call<Photo>
    @GET("collections/{id}/photos")
    fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("client_id") apikey: String,
        @Query("page") page:Int
    ): Call<CollectionPhotos>



}