package pics.app.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.work.Data
import androidx.work.WorkManager
import pics.app.data.*
import pics.app.data.photo.PhotoAPI
import pics.app.data.photo.model.Photo
import pics.app.repo.home.HomePagingSource
import pics.app.ui.base.Row
import pics.app.utils.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(
    private val service: PhotoAPI, private val context: Context
) :
    ViewModel() {
    var isReadPermissionGranted = false
    var isWritePermissionGranted = false
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val workManager = WorkManager.getInstance(context)
    private val _photoClicked = SingleLiveEvent<Photo>()
    val photoClicked: LiveData<Photo>
        get() = _photoClicked
    private val _downloadAction = SingleLiveEvent<Photo>()
    val downloadAction: LiveData<Photo>
        get() = _downloadAction
    val homePhotos = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        )
    )
    {
        HomePagingSource(service)

    }.liveData
        .map { pagingData -> pagingData.insertHeaderItem(item = Row.Header) }
        .cachedIn(viewModelScope)

    fun photoClicked(photo: Photo) {
        Timber.d("photo clicked is ${photo.id}")
        _photoClicked.value = photo
    }


    fun photoLongClick(photo: Photo): Boolean {
        _downloadAction.value = photo
        return true
    }

    override fun onCleared() {
        super.onCleared()
        workManager.cancelAllWork()
    }

    private fun getImageData(photo: Photo): Data {
        return Data.Builder()
            .putString(KEY_IMAGE_ID, photo.id)
            .putInt(KEY_IMAGE_WIDTH, photo.width)
            .putInt(KEY_IMAGE_HEIGHT, photo.height)
            .putString(KEY_IMAGE_COLOR, photo.color)
            .putString(KEY_IMAGE_URL, photo.urls.full)
            .putString(KEY_IMAGE_THUMBNAIL_URL, photo.urls.small)
            .build()
    }

    fun checkReadWritePermission() {

        val hasReadPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE

        ) == PackageManager.PERMISSION_GRANTED

        val hasWritePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

        ) == PackageManager.PERMISSION_GRANTED

        isReadPermissionGranted = hasReadPermission
        isWritePermissionGranted = hasWritePermission || isAboveSdk29

        val permissionsToRequest = mutableListOf<String>()
        if (!isReadPermissionGranted) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!isWritePermissionGranted) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!permissionsToRequest.isNullOrEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }

    }


}
