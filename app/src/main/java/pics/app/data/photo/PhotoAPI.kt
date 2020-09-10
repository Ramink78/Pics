package pics.app.data.photo

import pics.app.data.details.model.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoAPI {
    @GET("photos")
    fun getPhotos(
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): Single<ArrayList<Photo>>

    @GET("collections/{id}/photos")
    fun getPhotosFromCollection(
        @Query("id") id: Int?,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): List<Photo>

    @GET("photos/{id}")
    fun getPhoto(
        @Query("id") id: String?
    ): Photo

    @GET("photos/random")
    fun getRandom(
        @Query("count") count: Int
    ): Single<ArrayList<Photo>>

}