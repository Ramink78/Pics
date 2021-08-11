package pics.app.repo.saved

import androidx.lifecycle.asLiveData
import pics.app.database.AppDatabase
import pics.app.database.PhotosDao
import javax.inject.Inject

class SavedRepository @Inject constructor(private val appDatabase: AppDatabase) {

    val allSavedPhotos = appDatabase.getPhotosDao().getAllPhotos()
}