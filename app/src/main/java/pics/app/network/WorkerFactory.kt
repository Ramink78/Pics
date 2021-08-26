package pics.app.network

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import okhttp3.OkHttpClient
import pics.app.database.AppDatabase
import pics.app.database.SavePhotoWorker

class WorkerFactory(
    private val appDatabase: AppDatabase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            DownloadPhotoWorker::class.java.name -> {
                DownloadPhotoWorker(appContext, workerParameters)
            }
            SavePhotoWorker::class.java.name -> {
                SavePhotoWorker(appContext, workerParameters, appDatabase)
            }
            else -> null
        }
    }

}