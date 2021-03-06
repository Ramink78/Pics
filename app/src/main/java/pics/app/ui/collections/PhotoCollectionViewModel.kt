package pics.app.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import pics.app.PHOTO_PER_PAGE
import pics.app.data.photo.model.Photo
import pics.app.di.CollectionScope
import pics.app.repo.collection.CollectionPhotosPagingSource
import pics.app.ui.base.Row
import pics.app.utils.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject
@CollectionScope
class PhotoCollectionViewModel @Inject constructor(
    private val collectionPhotosPagingSource: CollectionPhotosPagingSource
):ViewModel() {
    private val _photoClick=SingleLiveEvent<Photo>()
    val photoClick:LiveData<Photo>
    get()=_photoClick
    val collectionPhotos = Pager(
        PagingConfig(
            pageSize = PHOTO_PER_PAGE,
            enablePlaceholders = false
        )
    ) {
        collectionPhotosPagingSource

    }.liveData
        .map { pagingData -> pagingData.insertHeaderItem(item = Row.Header) }
        .cachedIn(viewModelScope)

    fun onPhotoClick(photo: Photo){
        Timber.d("onClicked")
        _photoClick.value=photo
    }
}

