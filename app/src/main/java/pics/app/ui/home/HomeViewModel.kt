package pics.app.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.paging.*
import androidx.work.*
import pics.app.PHOTO_PER_PAGE
import pics.app.data.*
import pics.app.data.photo.PhotoAPI
import pics.app.data.photo.model.Photo
import pics.app.database.SavePhotoWorker
import pics.app.network.DownloadPhotoWorker
import pics.app.repo.home.HomePagingSource
import pics.app.ui.base.Row
import pics.app.utils.Quality
import pics.app.utils.SingleLiveEvent
import pics.app.utils.getImageUrl
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val service: PhotoAPI,
    private val context: Context
) :
    ViewModel() {
    init {
        Timber.d("home view Model")
    }


    var isReadPermissionGranted = false
    var isWritePermissionGranted = false
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val _qualityLiveData = MutableLiveData<Quality>()
    val qualityLiveData: LiveData<Quality>
        get() = _qualityLiveData

    private val _photoClicked = SingleLiveEvent<Photo>()
    val photoClicked: LiveData<Photo>
        get() = _photoClicked
    private val _downloadAction = SingleLiveEvent<Photo>()

    private val workManager = WorkManager.getInstance(context)
    val workerInfo: LiveData<List<WorkInfo>> =
        workManager.getWorkInfosByTagLiveData(DOWNLOAD_WORKER_TAG)

    val downloadAction: LiveData<Photo>
        get() = _downloadAction
    val homePhotos = Pager(
        PagingConfig(
            pageSize = PHOTO_PER_PAGE,
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

    fun beginDownload(photo: Photo, quality: Quality) {
        val inputData = workDataOf(
            KEY_IMAGE_ID to photo.id,
            KEY_IMAGE_WIDTH to photo.width,
            KEY_IMAGE_HEIGHT to photo.height,
            KEY_IMAGE_COLOR to photo.color,
            KEY_IMAGE_URL to getImageUrl(
                photo, quality
            ),
            KEY_IMAGE_THUMBNAIL_URL to photo.urls.small
        )
        var continuation =
            workManager.beginWith(
                OneTimeWorkRequestBuilder<DownloadPhotoWorker>()
                    .addTag(DOWNLOAD_WORKER_TAG)
                    .setInputData(inputData)
                    .build()
            )
        val saveToDatabaseRequest = OneTimeWorkRequest.from(SavePhotoWorker::class.java)

        continuation = continuation.then(saveToDatabaseRequest)
        continuation.enqueue()


    }

    fun setQuality(quality: Quality) {
        _qualityLiveData.value = quality
    }


}
