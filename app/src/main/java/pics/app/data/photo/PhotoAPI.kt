package pics.app.data.photo

import kotlinx.coroutines.flow.Flow
import pics.app.data.photo.model.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoAPI {
    @GET("photos")
    suspend fun  getPhotos(
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): List<Photo>



    @GET("photos/{id}")
    fun getPhoto(
        @Query("id") id: String?
    ): Photo


}