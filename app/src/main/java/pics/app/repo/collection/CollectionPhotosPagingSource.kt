package pics.app.repo.collection

import androidx.paging.PagingSource
import androidx.paging.PagingState
import pics.app.FIRST_PAGE
import pics.app.PHOTO_PER_PAGE
import pics.app.data.collections.CollectionsApi
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

        Timber.d("page  : $position")
        return try {
            val photos = service.getCollectionPhotos(id, position, params.loadSize)
            val nextKey = if (photos.isEmpty())
                null
            else
                position + 1
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
    override fun getRefreshKey(state: PagingState<Int, Row>):Int?{
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}