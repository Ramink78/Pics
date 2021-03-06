package pics.app.network

import androidx.work.DelegatingWorkerFactory
import pics.app.database.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactoryDelegation @Inject constructor(
    appDatabase: AppDatabase
) : DelegatingWorkerFactory() {
    init {
        addFactory(WorkerFactory(appDatabase))
    }
}