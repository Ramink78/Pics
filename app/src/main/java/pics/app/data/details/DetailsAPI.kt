package pics.app.data.details

import pics.app.data.photo.model.Photo
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsAPI {
    @GET("photos/{id}")
    suspend fun getDetails(
        @Path("id") id: String
    ): Photo
}