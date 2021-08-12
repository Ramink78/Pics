package pics.app.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pics.app.data.photo.model.Photo

@Dao
interface PhotosDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photo: SavedPhoto)

    @Query("SELECT * FROM photos ORDER BY id DESC")
    fun getAllPhotos(): Flow<List<SavedPhoto>>


}