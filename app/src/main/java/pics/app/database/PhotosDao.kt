package pics.app.database

import androidx.room.*
import pics.app.data.photo.model.Photo

@Dao
interface PhotosDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photo: SavedPhoto)


}