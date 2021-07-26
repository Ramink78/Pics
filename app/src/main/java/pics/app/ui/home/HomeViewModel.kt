package pics.app.ui.home

import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import pics.app.data.photo.PhotoAPI
import pics.app.data.photo.model.Photo
import pics.app.network.NetworkState
import pics.app.repo.explore.CollectionPhotosPagingSource
import pics.app.repo.home.HomePagingSource
import pics.app.utils.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(private val homePagingSource: HomePagingSource) : ViewModel() {
    var isHome=true
    private val _photoClicked=SingleLiveEvent<Photo>()
    val photoClicked:LiveData<Photo>
    get() = _photoClicked
    val homePhotos = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        )
    ) {
        homePagingSource

    }.liveData
        .cachedIn(viewModelScope)

fun photoClicked(photo: Photo)   {
    _photoClicked.value=photo
}


}
