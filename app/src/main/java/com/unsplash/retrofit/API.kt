package com.unsplash.retrofit


import com.unsplash.retrofit.data.collections.Collections
import com.unsplash.retrofit.data.collections.CollectionsData
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

//https://api.unsplash.com/photos/?client_id=Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0
interface API{
    @GET("search/photos/")
    fun getresult(@Query("client_id") key: String, @Query("query") quer: String): Call<Data>
    @GET("photos/")
    fun getRandom(@Query("client_id") key: String, @Query("page") page: Int,@Query("per_page") count: Int) :Call<Data>
    @GET("collections/")
    fun getCollections(@Query("client_id") key: String, @Query("page") page: Int,@Query("per_page") count: Int) :Call<Collections>




}