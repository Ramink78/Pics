package pics.app.ui.collections

import androidx.lifecycle.*
import androidx.paging.*
import pics.app.repo.explore.CollectionsPagingSource
import pics.app.COLLECTION_PER_PAGE
import pics.app.data.collections.model.Collection
import pics.app.di.CollectionScope
import pics.app.ui.base.Row
import pics.app.utils.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionsViewModel @Inject constructor(private val collectionsPagingSource: CollectionsPagingSource) :
    ViewModel() {

    private val _collectionClick=SingleLiveEvent<Collection>()
    val collectionClick:LiveData<Collection>
    get() = _collectionClick

    val collections = Pager(
        PagingConfig(
            pageSize = COLLECTION_PER_PAGE,
            enablePlaceholders = false
        )

    ) { collectionsPagingSource }
        .liveData
        .map { pagingData -> pagingData.insertHeaderItem(item = Row.Header) }
        .cachedIn(viewModelScope)
    fun collectionClick(collection: Collection){
        _collectionClick.value=collection

    }

}