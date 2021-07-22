package pics.app.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pics.app.data.details.DetailsAPI
import pics.app.data.photo.model.Photo
import pics.app.network.NetworkState
import timber.log.Timber
import javax.inject.Inject


class DetailPhotoViewModel @Inject constructor(
    private val detailsAPI: DetailsAPI
) : ViewModel() {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _detailPhoto = MutableLiveData<Photo>()
    val detailPhoto: LiveData<Photo>
        get() = _detailPhoto

    fun retrieveDetails(photoId: String) {
        _networkState.value = NetworkState.PROCESSING
        viewModelScope.launch {
            try {
                _detailPhoto.value = detailsAPI.getDetails(photoId)
                _networkState.value = NetworkState.SUCCESS

            } catch (e: Exception) {
                _networkState.value = NetworkState.ERROR
                Timber.d(e)


            }
        }

    }


}