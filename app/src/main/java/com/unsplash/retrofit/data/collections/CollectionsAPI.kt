package com.unsplash.retrofit.data.collections

import com.unsplash.retrofit.data.collections.model.Collection
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionsAPI {
    @GET("collections/")
    fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") count: Int
    ): Single<ArrayList<Collection>>
}