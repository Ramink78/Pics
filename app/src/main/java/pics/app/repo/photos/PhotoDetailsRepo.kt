package pics.app.repo.photos

import androidx.lifecycle.LiveData
import pics.app.data.details.DetailsAPI
import pics.app.data.details.model.Photo
import pics.app.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class PhotoDetailsRepo(private val service: DetailsAPI) {
    private lateinit var photosDataSource: PhotosDataSource
    fun fetchDetails(compositeDisposable: CompositeDisposable,photoId:String):LiveData<Photo>{
        photosDataSource= PhotosDataSource(service,compositeDisposable)
        photosDataSource.loadPhotos(photoId)
        return  photosDataSource.photo

    }
    fun getPhotoDetailNetworkState():LiveData<NetworkState>{
        return photosDataSource.networkstate
    }
}
