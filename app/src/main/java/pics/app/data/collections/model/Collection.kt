package pics.app.data.collections.model

import com.squareup.moshi.Json
import pics.app.data.photo.model.Photo
import pics.app.data.user.model.User

data class Collection(
    @Json(name = "id")
    val id:Int,
    @Json(name = "description")
    val description:String?,
    @Json(name = "title")
    val title:String,
    @Json(name ="total_photos")
    val totalPhotos:Int,
    @Json(name = "cover_photo")
    val coverPhoto:Photo,
    @Json(name = "user")
    val user:User
)