package pics.app

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import pics.app.di.AppComponent
import pics.app.di.DaggerAppComponent
import pics.app.network.WorkerFactory
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


class PicsApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(
            this
        )

    }

    @Inject
    lateinit var workerFactory: WorkerFactory


    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)
    }


}