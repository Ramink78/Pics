package pics.app.network

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import pics.app.data.download.DownloadService
import pics.app.database.AppDatabase
import pics.app.database.SavePhotoWorker
import javax.inject.Inject

class WorkerFactory(
    private val downloadService: DownloadService,
    private val appDatabase: AppDatabase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            DownloadPhotoWorker::class.java.name -> {
                DownloadPhotoWorker(appContext, downloadService, appDatabase, workerParameters)
            }
            SavePhotoWorker::class.java.name -> {
                SavePhotoWorker(appContext, workerParameters, appDatabase)
            }
            else -> null
        }
    }

}