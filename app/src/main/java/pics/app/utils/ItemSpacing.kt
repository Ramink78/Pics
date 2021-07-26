package pics.app.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import timber.log.Timber

class ItemSpacing(  private val spacing: Int,
                    private val spanCount: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val params = view.layoutParams as? StaggeredGridLayoutManager.LayoutParams
        val spanIndex = params?.spanIndex
        val position = params?.absoluteAdapterPosition ?: 0

        outRect.left = if (spanIndex == 0 &&position!=0) spacing else spacing/2
        outRect.top = if (position ==0) spacing else 0
        outRect.right = if (spanIndex==1) spacing else spacing/2
        outRect.bottom = spacing
    }
}