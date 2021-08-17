package pics.app.repo.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import pics.app.FIRST_PAGE
import pics.app.PHOTO_PER_PAGE
import pics.app.data.photo.PhotoAPI
import pics.app.ui.base.Row
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class HomePagingSource(
    private val service: PhotoAPI,
) : PagingSource<Int, Row>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Row> {
        val position = params.key ?: FIRST_PAGE
        return try {
            val photos = service.getPhotos(position, params.loadSize)

            val nextPage = if (photos.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                nextKey = nextPage,
                data = photos,
                prevKey = if (position == FIRST_PAGE) null else position - 1
            )

        } catch (exception: IOException) {
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