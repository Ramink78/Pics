package com.unsplash.retrofit.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.unsplash.retrofit.PER_PAGE
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.data.photo.PhotoAPI
import io.reactivex.disposables.CompositeDisposable

class HomePhotosRepo (val service:PhotoAPI){
    lateinit var homePagedList: LiveData<PagedList<Photo>>
    lateinit var homePhotoDataSourceFactory: PhotoDataSourceFactory
    fun fetchHomePhotos(compositeDisposable: CompositeDisposable): LiveData<PagedList<Photo>> {
        homePhotoDataSourceFactory= PhotoDataSourceFactory(service,compositeDisposable)
        val config= PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PER_PAGE)
            .build()
        homePagedList=LivePagedListBuilder(homePhotoDataSourceFactory,config).build()

        return homePagedList
    }
}