package com.unsplash.retrofit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.network.NetworkState
import com.unsplash.retrofit.repo.home.HomePhotosRepo
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(private val homePhotosRepo: HomePhotosRepo) : ViewModel() {
    private val compositeDisposable=CompositeDisposable()
    val homephotos:LiveData<PagedList<Photo>> by lazy {
        homePhotosRepo.fetchHomePhotos(compositeDisposable)
    }
    val networkstate:LiveData<NetworkState> by lazy {
        homePhotosRepo.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }




}