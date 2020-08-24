package com.unsplash.retrofit.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unsplash.retrofit.API
import com.unsplash.retrofit.Data
import com.unsplash.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val _photos = MutableLiveData<Data>()
    val photos: LiveData<Data> = _photos
    private var page = 1
    fun loadPhotos() {
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getPhotos(1, 30)
        call.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        _photos.value = result
                    }
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(context, "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loadMore() {
        page++
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getPhotos(page, 90)
        call.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        _photos.value = result
                    }
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(context, "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

}