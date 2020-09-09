package com.unsplash.retrofit.repo.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.unsplash.retrofit.PER_PAGE
import com.unsplash.retrofit.data.collections.CollectionsAPI
import com.unsplash.retrofit.data.collections.model.Collection
import com.unsplash.retrofit.network.NetworkState
import com.unsplash.retrofit.repo.home.HomePhotoDataSource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope

class CollectionRepo(private val service:CollectionsAPI) {
    lateinit var collectionPagedList:LiveData<PagedList<Collection>>
    lateinit var collectionDataSourceFactory: CollectionDataSourceFactory
    fun fetchCollections(compositeDisposable: CompositeDisposable):LiveData<PagedList<Collection>>{
        collectionDataSourceFactory= CollectionDataSourceFactory(service,compositeDisposable)
        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PER_PAGE)
            .setInitialLoadSizeHint(2)
            .build()
        collectionPagedList=LivePagedListBuilder(collectionDataSourceFactory,config).build()
        return collectionPagedList


    }
    fun getNetworkState() : LiveData<NetworkState>{
        return Transformations.switchMap(
            collectionDataSourceFactory.collectionDataSource,CollectionsDataSource::networkstate
        )

    }
}