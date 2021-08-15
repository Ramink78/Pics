package pics.app.repo.explore

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.PagingState
import pics.app.COLLECTION_PER_PAGE
import pics.app.FIRST_PAGE
import pics.app.PHOTO_PER_PAGE
import pics.app.data.collections.CollectionsApi
import pics.app.data.photo.model.Photo
import pics.app.ui.base.Row
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class CollectionPhotosPagingSource @Inject constructor(
    private val id: String,
    private val service: CollectionsApi,
) : PagingSource<Int, Row>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Row> {

        val position = params.key ?: FIRST_PAGE
        return try {

            val photos = service.getCollectionPhotos(id, position, PHOTO_PER_PAGE)

            val nextKey = if (photos.isNullOrEmpty()) {
                null
            } else {
                position + (params.loadSize / PHOTO_PER_PAGE)
            }
            LoadResult.Page(
                nextKey = nextKey,
                data = photos,
                prevKey = if (position == FIRST_PAGE) null else position - 1
            )

        } catch (exception: IOException) {
            Timber.d("my id is $id")

            return LoadResult.Error(exception)
        } catch (exception: HttpException) {

            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Row>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}