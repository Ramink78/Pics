package pics.app.ui.home

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import pics.app.data.photo.PhotoAPI
import pics.app.network.NetworkState
import pics.app.repo.home.HomePagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(private val service: PhotoAPI) : ViewModel() {
    val homePhotos = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        )
    ) {
        HomePagingSource(service)

    }.liveData
        .cachedIn(viewModelScope)


}
