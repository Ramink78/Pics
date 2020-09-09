package com.unsplash.retrofit


import com.unsplash.retrofit.data.collections.model.CollectionPhotos
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.data.random.ExploreData
import com.unsplash.retrofit.data.searchdata.Search
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API2 {
    @GET("search/photos/")
    fun getresult(@Query("client_id") key: String, @Query("query") quer: String): Single<Data>

    @GET("photos/")
    fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") count: Int
    ): Single<Data>



    @GET("photos/random")
    fun getRandom(
        @Query("client_id") key: String,
        @Query("count") count: Int
    ): Single<ExploreData>

    @GET("search/photos")
    fun photosFilter(
        @Query("client_id") key: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int,
        @Query("query") keyword: String
    ): Single<Search>

    @GET("photos/{id}")
    fun getDetails(
        @Path("id") id: String,
        @Query("client_id") apikey: String
    ): Single<Photo>
    @GET("collections/{id}/photos")
    fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("client_id") apikey: String,
        @Query("page") page:Int
    ): Single<CollectionPhotos>



}