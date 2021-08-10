package pics.app.network

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import okhttp3.ResponseBody
import pics.app.data.download.DownloadService
import pics.app.data.isAboveSdk29
import pics.app.database.AppDatabase
import pics.app.database.SavedPhoto
import pics.app.utils.PICS_DIR
import pics.app.utils.createPhotoUri
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
        var imageUri:Uri?=null
        return try {
            photoUrl?.let {
                val response = downloadService.downloadFile(photoUrl)
              imageUri=  response.savePhoto(
                    "${UUID.randomUUID()}.${response.contentType()?.subtype}"
                )
            }
            appDatabase.getPhotosDao().addPhoto(
                SavedPhoto(
                    photoId!!,
                    photoCreatedAt,
                    photoUpdatedAT,
                    photoWidth,
                    photoHeight,
                    imageUri.toString()
                )
            )
            Timber.d("image uri is: $imageUri")

            Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun ResponseBody.savePhoto(fileName: String):Uri? {
        val imageExtension = contentType()?.subtype
        val imageCollection =
            imageExtension?.let {
                createPhotoUri(
                    applicationContext,
                    fileName,
                    it,
                    contentLength()
                )
            }
        if (isAboveSdk29) {
            imageCollection?.let {
                applicationContext.contentResolver.openOutputStream(it, "w")?.use {os->
                    val byteCopied = byteStream().copyTo(os)
                    Timber.d("size is ${contentLength()} | copiedBytes is :$byteCopied")

                }
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
                    arrayOf("image/$imageExtension"), null
                )
                showDownloadingNotification("Download Finish", applicationContext)
            } else {
                showDownloadingNotification("Download Unsuccessful", applicationContext)

            }


        }
        return imageCollection

    }


}
