package pics.app.ui.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import pics.app.repo.explore.CollectionsPagingSource
import pics.app.COLLECTION_PER_PAGE
import javax.inject.Inject

class CollectionsViewModel @Inject constructor(private val collectionsPagingSource: CollectionsPagingSource) :
    ViewModel() {

    val collections = Pager(
        PagingConfig(
            pageSize = COLLECTION_PER_PAGE,
            enablePlaceholders = false
        )

    ) { collectionsPagingSource }
        .liveData
        .cachedIn(viewModelScope)

}