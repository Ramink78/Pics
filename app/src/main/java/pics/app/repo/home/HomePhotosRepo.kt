package pics.app.repo.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import pics.app.PER_PAGE
import pics.app.data.details.model.Photo
import pics.app.data.photo.PhotoAPI
import pics.app.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class HomePhotosRepo (private val service:PhotoAPI){
    lateinit var homePagedList: LiveData<PagedList<Photo>>
    lateinit var homePhotoDataSourceFactory: PhotoDataSourceFactory
    fun fetchHomePhotos(compositeDisposable: CompositeDisposable): LiveData<PagedList<Photo>> {
        homePhotoDataSourceFactory= PhotoDataSourceFactory(service,compositeDisposable)
        val config= PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PER_PAGE)
            .setInitialLoadSizeHint(2)
            .build()
        homePagedList=LivePagedListBuilder(homePhotoDataSourceFactory,config).build()

        return homePagedList
    }
    fun getNetworkState() : LiveData<NetworkState>{
        return Transformations.switchMap<HomePhotoDataSource,NetworkState>(
            homePhotoDataSourceFactory.homePhotoDataSource, HomePhotoDataSource::networkstate
        )

    }
}