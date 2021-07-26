package pics.app.data.collections.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pics.app.data.photo.model.Photo
import pics.app.data.user.model.User
import java.io.Serializable
@JsonClass(generateAdapter = true)
data class Collection(
    @Json(name = "id")
    val id:String,
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
):Serializable