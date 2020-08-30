package com.unsplash.retrofit

import com.unsplash.retrofit.data.photo.PhotoAPI
import com.unsplash.retrofit.data.photo.model.Photo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
const val FIRST_PAGE=1
const val PER_PAGE=30
object ServiceBuilder {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor(
            createHeader()
        )
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
