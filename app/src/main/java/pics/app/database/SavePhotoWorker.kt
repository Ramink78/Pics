package pics.app.database

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import pics.app.data.*
import timber.log.Timber
import java.util.*

class SavePhotoWorker(
    context: Context,
    parameters: WorkerParameters,
    private val appDatabase: AppDatabase
) :
    CoroutineWorker(context, parameters) {
    override suspend fun doWork(): Result {
        return try {
            val id = inputData.getString(KEY_IMAGE_ID)
            val width = inputData.getInt(KEY_IMAGE_WIDTH, 0)
            val height = inputData.getInt(KEY_IMAGE_HEIGHT, 0)
            val uri = inputData.getString(KEY_IMAGE_URI)
            val thumbUrl = inputData.getString(KEY_IMAGE_THUMBNAIL_URL)
            val color = inputData.getString(KEY_IMAGE_COLOR)

            appDatabase.getPhotosDao().addPhoto(
                SavedPhoto(
                    width = width,
                    height = height,
                    photoUri = uri,
                    thumbnailUrl = thumbUrl,
                    color = color,

                    )

            )

            Result.success()
        } catch (e: Exception) {
            Timber.d(e)
            Result.failure()
        }
    }
}