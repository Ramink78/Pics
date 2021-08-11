package pics.app.network

import androidx.work.DelegatingWorkerFactory
import pics.app.data.download.DownloadService
import pics.app.database.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactoryDelegation @Inject constructor(
    private val downloadService: DownloadService,
    private val appDatabase: AppDatabase
):DelegatingWorkerFactory() {
    init {
        addFactory(WorkerFactory(downloadService,appDatabase))
    }
}