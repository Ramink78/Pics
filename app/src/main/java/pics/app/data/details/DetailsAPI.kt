package pics.app.data.details

import pics.app.data.details.model.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsAPI {
    @GET("photos/{id}")
    fun getDetails(
        @Path("id") id: String
    ): Single<Photo>
}