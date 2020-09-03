package com.unsplash.retrofit.repo.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.data.photo.PhotoAPI
import io.reactivex.disposables.CompositeDisposable

class PhotoDataSourceFactory (private val service:PhotoAPI,private val compositeDisposable: CompositeDisposable )
    :DataSource.Factory<Int,Photo>() {
     val homePhotoDataSource=MutableLiveData<HomePhotoDataSource>()
    override fun create(): DataSource<Int, Photo> {
        val homeDataSource= HomePhotoDataSource(service,compositeDisposable)
        homePhotoDataSource.postValue(homeDataSource)
        return homeDataSource
    }
}