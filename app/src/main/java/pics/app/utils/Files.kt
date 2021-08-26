package pics.app.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File

private lateinit var values: ContentValues

val PICS_RELATIVE_PATH = "${Environment.DIRECTORY_PICTURES}${File.separator}Pics"
val PICS_DIR = "${
    Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES
    )
}${File.separator}Pics"

fun createPhotoUri(
    context: Context,
    fileName: String
): Uri? {
    val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.RELATIVE_PATH, PICS_RELATIVE_PATH)
            put(MediaStore.Images.Media.IS_PENDING, 1)

        }
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

    } else {
        values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        }
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    return context.contentResolver.insert(imageCollection, values)


}


// return a file name like-> photoId-width x height.jpg
fun generateImageFileName(photoId: String, width: Int, height: Int): String {
    return "${photoId}-${width}x${height}.jpg"

}