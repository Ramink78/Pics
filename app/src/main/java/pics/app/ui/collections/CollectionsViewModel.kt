package pics.app.ui.collections

import androidx.lifecycle.*
import androidx.paging.*
import pics.app.repo.collection.CollectionsPagingSource
import pics.app.COLLECTION_PER_PAGE
import pics.app.data.collections.CollectionsApi
import pics.app.data.collections.model.Collection
import pics.app.ui.base.Row
import pics.app.utils.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionsViewModel @Inject constructor(private val service: CollectionsApi) :
    ViewModel() {

    private val _collectionClick=SingleLiveEvent<Collection>()
    val collectionClick:LiveData<Collection>
    get() = _collectionClick

    val collections = Pager(
        PagingConfig(
            pageSize = COLLECTION_PER_PAGE,
            enablePlaceholders = false
        )

    ) { CollectionsPagingSource(service) }
        .liveData
        .map { pagingData -> pagingData.insertHeaderItem(item = Row.Header) }
        .cachedIn(viewModelScope)
    fun collectionClick(collection: Collection){
        _collectionClick.value=collection

    }

}