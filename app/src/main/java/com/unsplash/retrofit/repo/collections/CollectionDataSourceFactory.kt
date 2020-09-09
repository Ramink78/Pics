package com.unsplash.retrofit.repo.collections

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.unsplash.retrofit.data.collections.CollectionsAPI
import com.unsplash.retrofit.data.collections.model.Collection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope

class CollectionDataSourceFactory( val service: CollectionsAPI,  val compositeDisposable: CompositeDisposable):
    DataSource.Factory<Int, Collection>() {
     val collectionDataSource=MutableLiveData<CollectionsDataSource>()
    override fun create(): DataSource<Int, Collection> {
        val collectionsDataSource=CollectionsDataSource(service,compositeDisposable)
        collectionDataSource.postValue(collectionsDataSource)
        return collectionsDataSource
    }

}