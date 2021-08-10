package pics.app.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pics.app.data.isAboveSdk29
import pics.app.data.photo.model.Photo
import timber.log.Timber
import java.io.File

private lateinit var values: ContentValues
val PICS_RELATIVE_PATH = "${Environment.DIRECTORY_PICTURES}${File.separator}Pics"
val PICS_DIR = "${
    Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES
    )
}${File.separator}Pics"

suspend fun createPhotoUri(
    context: Context,
    fileName: String,
    size: Long
): Uri? {
    val imageCollection = if (isAboveSdk29) {
        values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.RELATIVE_PATH, PICS_RELATIVE_PATH)
            put(MediaStore.Images.Media.IS_PENDING, 1)
            put(MediaStore.Images.Media.SIZE, size)
        }
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

    } else {
        values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.SIZE, size)
        }
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }
    return withContext(Dispatchers.IO) {
        return@withContext context.contentResolver.insert(imageCollection, values)

    }
}


// return a file name like-> photoId-width x height.jpg
fun generateImageFileName(photoId: String, width: Int, height: Int): String {
    return "${photoId}-${width}x${height}.jpg"

}