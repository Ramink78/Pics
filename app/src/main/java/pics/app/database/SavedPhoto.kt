package pics.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import pics.app.data.user.model.User

@Entity(tableName = "photos")
data class SavedPhoto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val width: Int,
    val height: Int,
    val color: String?,
    val thumbnailUrl: String?,
    val photoUri: String?
)
