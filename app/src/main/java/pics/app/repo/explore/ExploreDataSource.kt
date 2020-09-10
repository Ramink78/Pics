package pics.app.repo.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import pics.app.FIRST_PAGE
import pics.app.PER_PAGE
import pics.app.data.details.model.Photo
import pics.app.data.photo.PhotoAPI
import pics.app.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ExploreDataSource(
    private val ExploreAPI: PhotoAPI,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Photo>() {
    private val _collections = MutableLiveData<ArrayList<Photo>>()
    val collections: LiveData<ArrayList<Photo>>
        get() = _collections

    var page = FIRST_PAGE
    private val _networkstate = MutableLiveData<NetworkState>()
    val networkstate: LiveData<NetworkState>
        get() = _networkstate

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        _networkstate.postValue(NetworkState.INITIALIZING)
        compositeDisposable.add(
            ExploreAPI.getRandom(PER_PAGE)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it, null, page + 1)
                    _networkstate.postValue(NetworkState.SUCCESS)

                }, {
                    _networkstate.postValue(NetworkState.ERROR)
                    Log.i(this::class.java.simpleName, "Error is : ${it.message}")
                })
        )


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        compositeDisposable.add(
            ExploreAPI.getRandom(PER_PAGE)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it, params.key + 1)
                    _networkstate.postValue(NetworkState.SUCCESS)
                }, {
                    _networkstate.postValue(NetworkState.ERROR)
                    Log.i(this::class.java.simpleName, "Error is : ${it.message}")
                }))


}






}