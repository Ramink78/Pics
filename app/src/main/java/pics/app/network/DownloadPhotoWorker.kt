package pics.app.network

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import okhttp3.ResponseBody
import pics.app.data.download.DownloadService
import pics.app.data.isAboveSdk29
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
        val photoUrl = inputData.getString("ImageUrl")
        val photoId = inputData.getString("ImageId")
        val photoCreatedAt = inputData.getString("ImageCreatedAt")
        val photoUpdatedAT = inputData.getString("ImageUpdatedAt")
        val photoWidth = inputData.getInt("ImageWidth", 0)
        val photoHeight = inputData.getInt("ImageHeight", 0)
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
            appDatabase.getPhotosDao().addPhoto(
                SavedPhoto(
                    photoId ?: "${UUID.randomUUID()}",
                    photoCreatedAt,
                    photoUpdatedAT,
                    photoWidth,
                    photoHeight,
                    imageUri.toString()
                )
            )

            Result.success()

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


}
