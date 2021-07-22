package pics.app.data.collections

import pics.app.data.collections.model.Collection
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionsApi {
    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") count: Int
    ): List<Collection>

}