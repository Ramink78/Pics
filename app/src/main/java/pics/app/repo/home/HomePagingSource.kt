package pics.app.repo.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import pics.app.FIRST_PAGE
import pics.app.PHOTO_PER_PAGE
import pics.app.data.photo.PhotoAPI
import pics.app.data.photo.model.Photo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomePagingSource @Inject constructor(
    private val service: PhotoAPI,
) : PagingSource<Int, Photo>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: FIRST_PAGE
        return try {
            val photos = service.getPhotos(position, PHOTO_PER_PAGE)
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