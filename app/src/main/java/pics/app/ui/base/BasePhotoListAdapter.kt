package pics.app.ui.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pics.app.data.PHOTO_TYPE
import pics.app.data.TITLE_TYPE

abstract class BasePhotoListAdapter :

    PagingDataAdapter<Row, RecyclerView.ViewHolder>(
        comparator
    ) {
    abstract val title: Int

    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Row>() {
            override fun areItemsTheSame(oldItem: Row, newItem: Row): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Row, newItem: Row): Boolean =
                oldItem == newItem
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            Row.Header -> TITLE_TYPE
            is Row.Content -> PHOTO_TYPE
            else -> throw IllegalStateException("Unknown viewType")
        }
    }


}
