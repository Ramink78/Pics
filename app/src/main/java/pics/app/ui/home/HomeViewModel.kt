package pics.app.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.launch
import pics.app.data.isAboveSdk29
import pics.app.data.photo.model.Photo
import pics.app.database.AppDatabase
import pics.app.database.SavedPhoto
import pics.app.network.DownloadPhotoWorker
import pics.app.repo.home.HomePagingSource
import pics.app.utils.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(
    private val homePagingSource: HomePagingSource, private val context: Context
    ) :

    ViewModel() {
    var isReadPermissionGranted = false
    var isWritePermissionGranted = false
     lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    val workManager = WorkManager.getInstance(context)
    private val _photoClicked = SingleLiveEvent<Photo>()
    val photoClicked: LiveData<Photo>
        get() = _photoClicked
    val homePhotos = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        )
    )
    {
        homePagingSource

    }.liveData
        .cachedIn(viewModelScope)

    fun photoClicked(photo: Photo) {
        _photoClicked.value = photo
    }

    fun photoLongClick(photo: Photo): Boolean {
        //start download
        Timber.d("Download Start")
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadPhotoWorker>()
            .setInputData(getImageUrlData(photo))
            .build()
        workManager.enqueue(
            downloadRequest
        )
        return true
    }

    override fun onCleared() {
        super.onCleared()
        workManager.cancelAllWork()
    }

    private fun getImageUrlData(photo: Photo): Data {
        return Data.Builder()
            .putString("ImageId",photo.id)
            .putInt("ImageWidth",photo.width)
            .putInt("ImageHeight",photo.height)
            .putString("ImageCreatedAt",photo.created_at)
            .putString("ImageUpdatedAt",photo.updated_at)
            .putString("ImageUrl", photo.urls.thumb)
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
