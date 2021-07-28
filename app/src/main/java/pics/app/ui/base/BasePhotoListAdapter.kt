package pics.app.uiPhoto.base

import androidx.annotation.StringRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePhotoListAdapter<T : Any, V : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) :

    PagingDataAdapter<T, V>(
        diffCallback
    ) {
    abstract val title: Int

}
