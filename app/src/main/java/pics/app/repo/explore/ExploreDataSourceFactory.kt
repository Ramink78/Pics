package pics.app.repo.explore

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import pics.app.data.photo.PhotoAPI
import pics.app.data.photo.model.Photo

class ExploreDataSourceFactory(
    private val service: PhotoAPI,
    val compositeDisposable: CompositeDisposable
) :
    DataSource.Factory<Int, Photo>() {
    val collectionDataSource = MutableLiveData<ExploreDataSource>()
    override fun create(): DataSource<Int, Photo> {
        val collectionsDataSource = ExploreDataSource(service, compositeDisposable)
        collectionDataSource.postValue(collectionsDataSource)
        return collectionsDataSource
    }

}