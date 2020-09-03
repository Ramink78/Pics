package com.unsplash.retrofit.repo.photos

import androidx.lifecycle.LiveData
import com.unsplash.retrofit.data.details.DetailsAPI
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class PhotoDetailsRepo(private val service: DetailsAPI) {
    private lateinit var photosDataSource: PhotosDataSource
    fun fetchDetails(compositeDisposable: CompositeDisposable,photoId:String):LiveData<Photo>{
        photosDataSource= PhotosDataSource(service,compositeDisposable)
        photosDataSource.loadPhotos(photoId)
        return  photosDataSource.photo

    }
    fun getPhotoDetailNetworkState():LiveData<NetworkState>{
        return photosDataSource.networkstate
    }
}
