package com.unsplash.retrofit

import com.unsplash.retrofit.data.photo.PhotoAPI
import com.unsplash.retrofit.data.photo.model.Photo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor(
            createHeader()
        )
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()



   fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
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
}
