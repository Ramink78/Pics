package com.unsplash.retrofit.ui.collections

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unsplash.retrofit.API
import com.unsplash.retrofit.ServiceBuilder
import com.unsplash.retrofit.data.API_KEY
import com.unsplash.retrofit.data.collections.CollectionPhotos
import com.unsplash.retrofit.data.details.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CollectionPhotosViewModel :ViewModel(){
     private val _photos=MutableLiveData<CollectionPhotos>()
    val photos:LiveData<CollectionPhotos> = _photos

    fun getCollectionPhotos(id: String,page:Int) {

        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getCollectionPhotos(id, API_KEY,page)
        call.enqueue(object : Callback<CollectionPhotos> {
            override fun onResponse(call: Call<CollectionPhotos>, response: Response<CollectionPhotos>) {
                if (response.isSuccessful) {
                    if(response.body()?.isNotEmpty()!!) {
                        Log.i("pages total","page is $page ")

                        _photos.value = response.body()
                    }

                }
            }

            override fun onFailure(call: Call<CollectionPhotos>, t: Throwable) {
            }
        })

    }
}