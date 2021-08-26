package pics.app.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pics.app.data.download.DownloadService
import pics.app.database.AppDatabase
import pics.app.network.WorkerFactory

@Module
class WorkerModule {

    @Provides
    fun provideWorkerFactory( appDatabase: AppDatabase):WorkerFactory{
        return WorkerFactory(appDatabase)
    }
}