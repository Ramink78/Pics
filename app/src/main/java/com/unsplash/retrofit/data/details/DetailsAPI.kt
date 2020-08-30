package com.unsplash.retrofit.data.details

import com.unsplash.retrofit.data.details.model.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsAPI {
    @GET("photos/{id}")
    fun getDetails(
        @Path("id") id: String
    ): Single<Photo>
}