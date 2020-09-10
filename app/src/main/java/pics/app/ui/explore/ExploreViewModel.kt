package pics.app.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import pics.app.data.details.model.Photo
import pics.app.network.NetworkState
import pics.app.repo.explore.ExploreRepo
import io.reactivex.disposables.CompositeDisposable

class ExploreViewModel(private val exploreRepo: ExploreRepo) : ViewModel() {
   private val compositeDisposable=CompositeDisposable()

    val randomPhotos:LiveData<PagedList<Photo>> by lazy {
        exploreRepo.fetchExplore(compositeDisposable)
    }
    val networkState:LiveData<NetworkState> by lazy {
        exploreRepo.getNetworkState()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}