package pics.app.di

import dagger.Module
import dagger.Provides
import pics.app.data.download.DownloadService
import pics.app.database.AppDatabase
import pics.app.network.WorkerFactory

@Module
class WorkerModule {

    @Provides
    fun provideWorkerFactory(downloadService:DownloadService, appDatabase: AppDatabase):WorkerFactory{
        return WorkerFactory(downloadService,appDatabase)
    }
}