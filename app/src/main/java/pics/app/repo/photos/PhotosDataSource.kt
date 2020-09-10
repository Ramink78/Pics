package pics.app.repo.photos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pics.app.data.details.DetailsAPI
import pics.app.data.details.model.Photo
import pics.app.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.Exception

class PhotosDataSource(private val service:DetailsAPI, private val compositeDisposable: CompositeDisposable) {
    private val _networkstate=MutableLiveData<NetworkState>()
    val networkstate:LiveData<NetworkState>
    get() = _networkstate

    private val _photo=MutableLiveData<Photo>()
    val photo:LiveData<Photo>
    get() = _photo

    fun loadPhotos(photoId:String){
        _networkstate.postValue(NetworkState.PROSSECING)

        try {
            compositeDisposable.add(
                service.getDetails(photoId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _photo.postValue(it)
                        _networkstate.postValue(NetworkState.SUCCESS)

                    },{
                       _networkstate.postValue(NetworkState.ERROR)
                        Log.i(this::class.java.simpleName,"Error is: ${it.message}")

                    })
            )

        }
        catch(e :Exception) {
            Log.i(this::class.java.simpleName,"Error is: ${e.message}")

        }
    }

}