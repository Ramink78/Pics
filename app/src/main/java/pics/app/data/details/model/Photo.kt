package pics.app.data.details.model

import com.google.gson.annotations.SerializedName
import pics.app.Urls
import pics.app.User
import java.io.Serializable

data class Photo(
    @SerializedName("user")
    val user: User, @SerializedName("width")
    val width: Int, @SerializedName("height")
    val height: Int, @SerializedName("location")
    val location: Location, @SerializedName("exif")
    val exif: Exifs, @SerializedName("color")
    val color: String, @SerializedName("id")
    val id: String, @SerializedName("created_at")
    val createdAt: String, @SerializedName("urls")
    val urls: Urls,
) : Serializable
