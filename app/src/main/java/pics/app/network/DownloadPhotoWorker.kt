package pics.app.network

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import okhttp3.ResponseBody
import pics.app.data.*
import pics.app.data.download.DownloadService
import pics.app.database.AppDatabase
import pics.app.database.SavedPhoto
import pics.app.utils.PICS_DIR
import pics.app.utils.createPhotoUri
import pics.app.utils.generateImageFileName
import pics.app.utils.showDownloadingNotification
import timber.log.Timber
import java.io.File
import java.util.*


class DownloadPhotoWorker(
    context: Context,
    private val downloadService: DownloadService,
    private val appDatabase: AppDatabase,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Timber.d("download start")
        showDownloadingNotification("Downloading photo...", applicationContext)
        val photoUrl = inputData.getString(KEY_IMAGE_URL)
        val thumbnailUrl = inputData.getString(KEY_IMAGE_THUMBNAIL_URL)
        val photoId = inputData.getString(KEY_IMAGE_ID)
        val photoColor = inputData.getString(KEY_IMAGE_COLOR)
        val photoCreatedAt = inputData.getString(KEY_IMAGE_CREATED_AT)
        val photoWidth = inputData.getInt(KEY_IMAGE_WIDTH, 0)
        val photoHeight = inputData.getInt(KEY_IMAGE_HEIGHT, 0)

        var imageUri: Uri? = null
        val fileName =
            generateImageFileName(photoId ?: "${UUID.randomUUID()}", photoWidth, photoHeight)
        return try {
            photoUrl?.let {
                val response = downloadService.downloadFile(photoUrl)
                imageUri = response.savePhoto(
                    "$fileName.jpg"
                )
            }
            Result.success(
                createOutputData(
                    SavedPhoto(
                        photoId ?: "${UUID.randomUUID()}",
                        photoCreatedAt,
                        photoWidth,
                        photoHeight,
                        photoColor,
                        thumbnailUrl,
                        imageUri.toString()
                    )
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun ResponseBody.savePhoto(fileName: String): Uri? {
        val imageCollection =
            createPhotoUri(
                applicationContext,
                fileName,
                contentLength()
            )

        if (isAboveSdk29) {
            imageCollection?.let { uri ->
                applicationContext.contentResolver.openOutputStream(uri, "w")?.use { os ->
                    val byteCopied = byteStream().copyTo(os)
                    Timber.d("size is ${contentLength()} | copiedBytes is :$byteCopied")

                }
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.IS_PENDING, 0)
                }
                applicationContext.contentResolver.update(uri, values, null, null)
            }
        } else {
            val picsDir = File(PICS_DIR)
            if (!picsDir.exists()) {
                picsDir.mkdir()
            }
            val photoFile = File(picsDir, fileName)
            val byteCopied = byteStream().copyTo(photoFile.outputStream())
            Timber.d("size is ${contentLength()} | copiedBytes is :$byteCopied")

            if (byteCopied == contentLength()) {
                MediaScannerConnection.scanFile(
                    applicationContext, arrayOf(photoFile.absolutePath),
                    arrayOf("image/jpeg"), null
                )
                showDownloadingNotification("Download Finish", applicationContext)
            } else {
                showDownloadingNotification("Download Unsuccessful", applicationContext)

            }


        }
        return imageCollection

    }

    private fun createOutputData(savedPhoto: SavedPhoto): Data =
        Data.Builder()
            .putInt(KEY_IMAGE_WIDTH, savedPhoto.width)
            .putInt(KEY_IMAGE_HEIGHT, savedPhoto.height)
            .putString(KEY_IMAGE_URI, savedPhoto.photoUri)
            .putString(KEY_IMAGE_COLOR, savedPhoto.color)
            .putString(KEY_IMAGE_THUMBNAIL_URL, savedPhoto.thumbnailUrl)
            .putString(KEY_IMAGE_ID, savedPhoto.id)
            .build()

}
