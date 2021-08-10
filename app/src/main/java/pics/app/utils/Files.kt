package pics.app.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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


inline fun <T> isAbove28Sdk(block: () -> T): T? {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        block()
    } else {
        null
    }


}

suspend fun getPhotoUri(
    fileName: String,
    imageExtension: String,
    size: Long,
    context: Context
): Uri? {
    val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } else {
        Uri.fromFile(File(PICS_DIR))
        //   imageCollection = Uri.fromFile(File(PICS_DIR))
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/$imageExtension")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.RELATIVE_PATH, PICS_RELATIVE_PATH)
            put(MediaStore.Images.Media.SIZE, size)

        }
        context.contentResolver.insert(imageCollection, values)
    } else {
        val picsFile = File(PICS_DIR)
        if (!picsFile.exists()) {
            picsFile.mkdirs()
        }

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            /* put(MediaStore.Images.Media.TITLE, fileName)
             put(MediaStore.Images.Media.MIME_TYPE, "image/$imageExtension")
             put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
             put(MediaStore.Images.Media.SIZE, size)*/
        }
        return withContext(Dispatchers.IO) {
            val uri = context.contentResolver.insert(imageCollection, values)
            Timber.d("uri is: ${uri.toString()}")
            return@withContext uri


        }


    }


}

suspend fun createPhotoUri(
    context: Context,
    fileName: String,
    imageExtension: String,
    size: Long
): Uri? {
    val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/$imageExtension")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.RELATIVE_PATH, PICS_RELATIVE_PATH)
            put(MediaStore.Images.Media.SIZE, size)
        }
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

    } else {
        values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/$imageExtension")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.SIZE, size)
        }
        Timber.d("legacy uri is ${Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()+ PICS_RELATIVE_PATH)}")
      MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }


    return withContext(Dispatchers.IO) {
        return@withContext context.contentResolver.insert(imageCollection, values)

}
}


// return a file name like-> photographerUsername-photoId-width x height.extension
fun generateImageFileName(photo: Photo, extension: String): String {
    return "${photo.user?.name}-${photo.id}-${photo.width}x${photo.height}.$extension"

}