package com.unsplash.retrofit.repo.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.unsplash.retrofit.FIRST_PAGE
import com.unsplash.retrofit.PER_PAGE
import com.unsplash.retrofit.data.collections.CollectionsAPI
import com.unsplash.retrofit.data.collections.model.Collection
import com.unsplash.retrofit.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CollectionsDataSource(
    private val collectionsAPI: CollectionsAPI,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Collection>() {
    private val _collections = MutableLiveData<ArrayList<Collection>>()
    val collections: LiveData<ArrayList<Collection>>
        get() = _collections

    var page = FIRST_PAGE
    private val _networkstate = MutableLiveData<NetworkState>()
    val networkstate: LiveData<NetworkState>
        get() = _networkstate

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Collection>
    ) {
        _networkstate.postValue(NetworkState.INITIALIZING)
        compositeDisposable.add(
            collectionsAPI.getCollections(FIRST_PAGE, params.requestedLoadSize)
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Collection>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Collection>) {
        compositeDisposable.add(
            collectionsAPI.getCollections(params.key, PER_PAGE)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it, params.key + 1)
                    _networkstate.postValue(NetworkState.SUCCESS)
                }, {
                    _networkstate.postValue(NetworkState.ERROR)
                    Log.i(this::class.java.simpleName, "Error is : ${it.message}")
                }))


}






}