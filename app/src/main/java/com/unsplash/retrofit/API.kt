package com.unsplash.retrofit


import com.unsplash.retrofit.data.collections.Collections
import com.unsplash.retrofit.data.random.Explore
import com.unsplash.retrofit.data.random.ExploreData
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("search/photos/")
    fun getresult(@Query("client_id") key: String, @Query("query") quer: String): Call<Data>

    @GET("photos/")
    fun getPhotos(
        @Query("client_id") key: String,
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


}