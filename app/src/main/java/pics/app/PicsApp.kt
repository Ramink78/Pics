package pics.app

import android.app.Application
import pics.app.di.AppComponent
import pics.app.di.DaggerAppComponent
import timber.log.Timber

import timber.log.Timber.DebugTree




class PicsApp:Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }


}