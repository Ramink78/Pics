package pics.app.network

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.work.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import okhttp3.ResponseBody
import okio.BufferedSink
import okio.IOException
import okio.buffer
import okio.sink
import pics.app.R
import pics.app.data.*
import pics.app.database.SavedPhoto
import pics.app.utils.*
import timber.log.Timber
import java.io.File
import java.util.*


class DownloadPhotoWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val photoUrl = inputData.getString(KEY_IMAGE_URL) ?: return Result.failure()
        val thumbnailUrl = inputData.getString(KEY_IMAGE_THUMBNAIL_URL)
        val photoId = inputData.getString(KEY_IMAGE_ID)
        val photoColor = inputData.getString(KEY_IMAGE_COLOR)
        val photoWidth = inputData.getInt(KEY_IMAGE_WIDTH, 0)
        val photoHeight = inputData.getInt(KEY_IMAGE_HEIGHT, 0)

        val fileName =
            generateImageFileName(photoId ?: "${UUID.randomUUID()}", photoWidth, photoHeight)

        setForegroundAsync(createForegroundInfo(0, ""))
        val imageCollection = createPhotoUri(applicationContext, fileName)


        downloadImage(
            photoUrl,
            fileName,
            imageCollection
        )
        return Result.success(
            createOutputData(
                SavedPhoto(
                    width = photoWidth,
                    height = photoHeight,
                    color = photoColor,
                    thumbnailUrl = thumbnailUrl,
                    photoUri = " "
                )
            )
        )


    }


    private fun ResponseBody.savePhoto(
        fileName: String,
        imageCollection: Uri?,
        onProgress: ((Int, String) -> Unit)?
    ): Uri? {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageCollection?.let { uri ->
                applicationContext.contentResolver.openOutputStream(uri, "w")?.use { os ->
                    writeToSink(os.sink().buffer(), onProgress)
                }
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.IS_PENDING, 0)
                }
                applicationContext.contentResolver.update(uri, values, null, null)
            }
            return imageCollection
        } else {
            val picsDir = File(PICS_DIR)
            if (!picsDir.exists()) {
                picsDir.mkdir()
            }
            val photoFile = File(picsDir, fileName)
            val isComplete = writeToSink(photoFile.sink().buffer(), onProgress)
            if (isComplete) {
                MediaScannerConnection.scanFile(
                    applicationContext, arrayOf(photoFile.absolutePath),
                    arrayOf("image/jpeg"), null
                )
            } else {

                if (photoFile.exists())
                    photoFile.delete()
                throw  CancellationException("Download Canceled")
            }

            return FileProvider.getUriForFile(
                applicationContext,
                "${applicationContext.packageName}.provider",
                photoFile
            )

        }

    }

    private fun createOutputData(savedPhoto: SavedPhoto): Data =
        workDataOf(
            KEY_IMAGE_WIDTH to savedPhoto.width,
            KEY_IMAGE_HEIGHT to savedPhoto.height,
            KEY_IMAGE_URI to savedPhoto.photoUri,
            KEY_IMAGE_COLOR to savedPhoto.color,
            KEY_IMAGE_THUMBNAIL_URL to savedPhoto.thumbnailUrl,
            KEY_IMAGE_ID to savedPhoto.id
        )

    private fun ResponseBody.writeToSink(
        sink: BufferedSink,
        onProgress: ((Int, String) -> Unit)?
    ): Boolean {
        val fileSize = contentLength()
        var totalBytesRead = 0L
        var progressToReport = 0

        while (true) {
            if (isStopped)
                return false
            val readCount = source().read(sink.buffer, 8192L)
            if (readCount == -1L) break
            sink.emit()

            totalBytesRead += readCount
            val status = "${totalBytesRead.toDouble().humanReadable()}/${
                fileSize.toDouble().humanReadable()
            }"
            val progress = (100.0 * totalBytesRead / fileSize)
            if (progress - progressToReport >= 5) {
                progressToReport = progress.toInt()
                onProgress?.invoke(progressToReport, status)
            }
        }

        sink.close()
        return true
    }

    private fun createForegroundInfo(
        progress: Int,
        status: String
    ): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context)
        }
        val cancel = applicationContext.getString(R.string.notification_cancel)
        val title = applicationContext.getString(R.string.notification_title)
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        val notif = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_app)
            .setOnlyAlertOnce(true)
            .setContentTitle(title)
            .setContentTitle(title)
            .setContentText(status)
            .setProgress(100, progress, false)
            .setOngoing(true)
            .addAction(R.drawable.details_camera_make, cancel, intent)
            .build()


        return ForegroundInfo(id.hashCode(), notif)
    }


    private suspend fun downloadImage(photoUrl: String, fileName: String, imageCollection: Uri?) {

        val request = Request.Builder()
            .url(photoUrl)
            .build()
        withContext(Dispatchers.IO) {
            try {
                HttpClient.makeRequest(request)
                    .use { response ->
                        Timber.d(response.message)
                        if (!response.isSuccessful) {
                            showErrorNotif(applicationContext)
                            throw IOException("download failed")
                        }
                        val uri = response.body?.savePhoto(
                            fileName,
                            imageCollection
                        ) { progress, status ->
                            launch {
                                setForeground(createForegroundInfo(progress, status))
                                setProgress(workDataOf(PROGRESS_KEY to progress))
                            }
                        }
                        if (uri != null) {
                            showCompletedDownloadNotif(
                                applicationContext,
                                fileName,
                                uri

                            )
                        }
                    }
            } catch (e: Exception) {
                showErrorNotif(applicationContext)
                Timber.e(e)
            }
        }


    }


}


