package pics.app.repo.explore

import androidx.paging.PagingSource
import androidx.paging.PagingState
import pics.app.COLLECTION_PER_PAGE
import pics.app.FIRST_PAGE
import pics.app.data.collections.CollectionsApi
import pics.app.data.collections.model.Collection
import pics.app.ui.base.Row
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CollectionsPagingSource @Inject constructor(
    private val collectionsApi: CollectionsApi,
) : PagingSource<Int, Row>() {
    override fun getRefreshKey(state: PagingState<Int, Row>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Row> {
        val position = params.key ?: FIRST_PAGE

        return try {
            val response = collectionsApi.getCollections(position, COLLECTION_PER_PAGE)
            val nextKey = if (response.isNullOrEmpty())
                null
            else
                position + (params.loadSize / COLLECTION_PER_PAGE)
            LoadResult.Page(
                nextKey = nextKey,
                prevKey = if (position == FIRST_PAGE) null else position - 1,
                data = response
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }


    }


}






