package pics.app.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import pics.app.repo.explore.CollectionsPagingSource
import pics.app.COLLECTION_PER_PAGE
import pics.app.data.collections.model.Collection
import pics.app.di.CollectionScope
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
        .cachedIn(viewModelScope)
    fun collectionClick(collection: Collection){
        _collectionClick.value=collection

    }

}