package pics.app.ui.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import pics.app.PHOTO_PER_PAGE
import pics.app.data.photo.PhotoAPI
import pics.app.repo.explore.CollectionPhotosPagingSource
import javax.inject.Inject

class PhotoCollectionViewModel @Inject constructor(
    private val collectionPhotosPagingSource: CollectionPhotosPagingSource
):ViewModel() {
    val collectionPhotos = Pager(
        PagingConfig(
            pageSize = PHOTO_PER_PAGE,
            enablePlaceholders = false
        )
    ) {
        collectionPhotosPagingSource

    }.liveData
        .cachedIn(viewModelScope)
}