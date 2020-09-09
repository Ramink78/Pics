package com.unsplash.retrofit.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.unsplash.retrofit.data.collections.model.Collection
import com.unsplash.retrofit.network.NetworkState
import com.unsplash.retrofit.repo.collections.CollectionRepo
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope

class CollectionsViewModel(private val collectionRepo: CollectionRepo) : ViewModel() {
    private val compositeDisposable= CompositeDisposable()

    val collections:LiveData<PagedList<Collection>> by lazy {
        collectionRepo.fetchCollections(compositeDisposable)
    }
    val networkState:LiveData<NetworkState> by lazy {
        collectionRepo.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}