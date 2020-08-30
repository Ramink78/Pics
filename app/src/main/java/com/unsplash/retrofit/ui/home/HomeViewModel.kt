package com.unsplash.retrofit.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.unsplash.retrofit.API
import com.unsplash.retrofit.Data
import com.unsplash.retrofit.ServiceBuilder
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.network.NetworkState
import com.unsplash.retrofit.repo.HomePhotosRepo
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val homePhotosRepo: HomePhotosRepo) : ViewModel() {
    private val compositeDisposable=CompositeDisposable()
    val homephotos:LiveData<PagedList<Photo>> by lazy {
        homePhotosRepo.fetchHomePhotos(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }




}