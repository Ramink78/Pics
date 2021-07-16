package pics.app.repo.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import pics.app.PER_PAGE
import pics.app.data.photo.PhotoAPI
import pics.app.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import pics.app.data.photo.model.Photo

class ExploreRepo(private val service: PhotoAPI) {
    lateinit var collectionPagedList:LiveData<PagedList<Photo>>
    lateinit var exploreDataSourceFactory: ExploreDataSourceFactory
    fun fetchExplore(compositeDisposable: CompositeDisposable):LiveData<PagedList<Photo>>{
        exploreDataSourceFactory= ExploreDataSourceFactory(service,compositeDisposable)
        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PER_PAGE)
            .setInitialLoadSizeHint(2)
            .build()
        collectionPagedList=LivePagedListBuilder(exploreDataSourceFactory,config).build()
        return collectionPagedList


    }
    fun getNetworkState() : LiveData<NetworkState>{
        return Transformations.switchMap(
            exploreDataSourceFactory.collectionDataSource, ExploreDataSource::networkstate
        )

    }
}