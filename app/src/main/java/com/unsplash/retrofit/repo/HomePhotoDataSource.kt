package com.unsplash.retrofit.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.unsplash.retrofit.FIRST_PAGE
import com.unsplash.retrofit.PER_PAGE
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.data.photo.PhotoAPI
import com.unsplash.retrofit.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePhotoDataSource(
    private val service: PhotoAPI,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Photo>() {
    private val _networkstate = MutableLiveData<NetworkState>()
    val networkstate: LiveData<NetworkState>
        get() = _networkstate

    private var page = FIRST_PAGE
    private val _photos = MutableLiveData<ArrayList<Photo>>()
    val photos: LiveData<ArrayList<Photo>>
        get() = _photos

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        _networkstate.postValue(NetworkState.PROSSECING)
        compositeDisposable.add(
            service.getPhotos(params.key, PER_PAGE)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it, params.key + 1)
                    _networkstate.postValue(NetworkState.SUCCESS)
                }, {
                    _networkstate.postValue(NetworkState.ERROR)
                    Log.i(this::class.java.simpleName, "Error is : ${it.message}")
                })

        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Photo>
    ) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        _networkstate.postValue(NetworkState.PROSSECING)
        compositeDisposable.add(
            service.getPhotos(page, PER_PAGE)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it, null, page + 1)
                    _networkstate.postValue(NetworkState.SUCCESS)
                }, {
                    _networkstate.postValue(NetworkState.ERROR)
                    Log.i(this::class.java.simpleName, "Error is : ${it.message}")
                })

        )
    }


}