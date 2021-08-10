package pics.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import pics.app.data.user.model.User

@Entity(tableName = "photos")
data class SavedPhoto(
    @PrimaryKey val id: String,
    val created_at: String?,
    val updated_at: String?,
    val width: Int?,
    val height: Int?,
    val photoUri:String?
)
