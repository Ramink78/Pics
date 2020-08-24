package com.unsplash.retrofit.network

import com.unsplash.retrofit.ServiceBuilder
import com.unsplash.retrofit.data.photo.PhotoAPI
import com.unsplash.retrofit.data.photo.model.Photo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkService {
    private val service: PhotoAPI
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor(
            createHeader()
        )
        .build()
    init {
        val retrofit2: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service =retrofit2.create(PhotoAPI::class.java)
    }
    private fun createHeader(): Interceptor {
        return Interceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Client-ID Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0")
                .build()
            it.proceed(request)

        }
    }
    suspend fun getPhotos(page:Int,per_page:Int):ArrayList<Photo>{
        return service.getPhotos(page, per_page)

    }
}