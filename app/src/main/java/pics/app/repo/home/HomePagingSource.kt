package pics.app.repo.home

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import pics.app.FIRST_PAGE
import pics.app.PER_PAGE
import pics.app.data.photo.model.Photo
import pics.app.data.photo.PhotoAPI
import retrofit2.HttpException
import java.io.IOException

class HomePagingSource(
    private val service: PhotoAPI,
) : PagingSource<Int, Photo>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: FIRST_PAGE
        Log.d("HomePagingSource", "page number is: $position")
        return try {
            val photos = service.getPhotos(position, PER_PAGE)
            val nextKey = if (photos.isNullOrEmpty()) {
                null
            } else {
                position + (params.loadSize / PER_PAGE)
            }
            LoadResult.Page(
                nextKey = nextKey,
                data = photos,
                prevKey = if (position == 1) null else position - 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}