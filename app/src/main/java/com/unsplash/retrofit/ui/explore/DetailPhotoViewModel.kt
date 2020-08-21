package com.unsplash.retrofit.ui.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unsplash.retrofit.API
import com.unsplash.retrofit.ServiceBuilder
import com.unsplash.retrofit.data.API_KEY
import com.unsplash.retrofit.data.details.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPhotoViewModel : ViewModel() {

    private val _result = MutableLiveData<Photo>()
    val result: LiveData<Photo>
        get() = _result

    fun getDetails(id: String) {

        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getDetails(id, API_KEY)
        call.enqueue(object : Callback<Photo> {
            override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                if (response.isSuccessful) {
                    _result.value = response.body()
                    Log.i("in view model", "hi ramin")

                }
            }

            override fun onFailure(call: Call<Photo>, t: Throwable) {
            }
        })

    }

}