package pics.app.data.collections

import pics.app.data.collections.model.Collection
import pics.app.data.photo.model.Photo
import pics.app.di.CollectionScope
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsApi {
    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") count: Int
    ): List<Collection>
    @GET("collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") id: String?,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): List<Photo>
}