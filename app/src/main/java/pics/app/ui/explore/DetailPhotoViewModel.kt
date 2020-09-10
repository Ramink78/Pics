package pics.app.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pics.app.data.details.model.Photo
import pics.app.network.NetworkState
import pics.app.repo.photos.PhotoDetailsRepo
import io.reactivex.disposables.CompositeDisposable

class DetailPhotoViewModel(private val photoRepo: PhotoDetailsRepo, photoId:String) : ViewModel() {
    private val compositeDisposable=CompositeDisposable()
    val photoDetails:LiveData<Photo> by lazy {
        photoRepo.fetchDetails(compositeDisposable,photoId)

    }
    val networkState:LiveData<NetworkState> by  lazy {
        photoRepo.getPhotoDetailNetworkState()
    }

    private val _result = MutableLiveData<Photo>()



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}