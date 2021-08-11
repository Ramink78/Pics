package pics.app.ui

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pics.app.database.AppDatabase
import pics.app.database.SavedPhoto
import pics.app.repo.saved.SavedRepository
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
    private val savedRepository: SavedRepository
) : ViewModel() {
    val localPhotos: LiveData<List<SavedPhoto>> =
        savedRepository.allSavedPhotos.asLiveData()


}