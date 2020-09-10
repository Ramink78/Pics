package pics.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import pics.app.data.details.model.Photo
import pics.app.network.NetworkState
import pics.app.repo.home.HomePhotosRepo
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(private val homePhotosRepo: HomePhotosRepo) : ViewModel() {
    private val compositeDisposable=CompositeDisposable()
    val homephotos:LiveData<PagedList<Photo>> by lazy {
        homePhotosRepo.fetchHomePhotos(compositeDisposable)
    }
    val networkstate:LiveData<NetworkState> by lazy {
        homePhotosRepo.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }




}