package pics.app.data.photo.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import pics.app.data.user.model.User
import pics.app.ui.base.Row
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Photo(
    override val id: String,
    val created_at: String?,
    val updated_at: String?,
    val width: Int,
    val height: Int,
    val color: String? = "#E0E0E0",
    val views: Int?,
    val downloads: Int?,
    val likes: Int?,
    var liked_by_user: Boolean?,
    val description: String?,
    val alt_description: String?,
    val exif: Exif?,
    val location: Location?,
    val urls: Urls,
    val user: User?
) : Row.Content(id), Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class Exif(
    @Json(name = "make")
    val make: String?,
    @Json(name = "model")
    val model: String?,
    @Json(name = "exposure_time")
    val exposureTime: String?,
    @Json(name = "aperture")
    val aperture: String?,
    @Json(name = "focal_length")
    val focalLength: String?,
    @Json(name = "iso")
    val iso: String?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "title")
    val title: String?,
    @Json(name = "name")
    val name: String? = "none",
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Urls(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val thumb: String,
    val small: String?
) : Parcelable
