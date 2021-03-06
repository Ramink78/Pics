package pics.app.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.detail_header.view.*
import pics.app.R
import pics.app.ui.base.DetailRow
import pics.app.ui.viewholder.DetailCategoryViewHolder
import pics.app.ui.viewholder.DetailChildViewHolder
import pics.app.ui.viewholder.DetailHeaderViewHolder
import timber.log.Timber
import javax.inject.Inject

class DetailsAdapter @Inject constructor(
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val detailList = arrayListOf<DetailRow>()
    private var separatorIsExpanded = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.detail_category -> DetailCategoryViewHolder.from(parent)
            R.layout.detail_child -> DetailChildViewHolder.from(parent)
            R.layout.detail_header -> DetailHeaderViewHolder.from(parent)
            else -> throw  ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemCount(): Int {
        return detailList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (detailList[position]) {
            is DetailRow.HeaderPhoto -> R.layout.detail_header
            is DetailRow.Category -> R.layout.detail_category
            is DetailRow.Child -> R.layout.detail_child
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailHeaderViewHolder ->
                holder.apply {
                    val photo = (detailList[position] as DetailRow.HeaderPhoto).photo
                    val ratio = photo.width.toFloat() / photo.height
                    ConstraintSet().apply {
                        val root = itemView.rootView as ConstraintLayout
                        clone(root)
                        setDimensionRatio(itemView.detail_header_image_view.id, ratio.toString())
                        applyTo(root)
                    }
                    bind(photo)
                }

            is DetailCategoryViewHolder -> {
                val category = (detailList[position] as DetailRow.Category)
                holder.bind(context.resources.getString(category.title))
            }
            is DetailChildViewHolder -> {
                val detailItem = (detailList[position] as DetailRow.Child)
                holder.bind(
                    primaryText = detailItem.primaryText,
                    secondaryText = context.resources.getString(detailItem.secondaryText)

                )
            }


        }
    }

    fun submitList(list: List<DetailRow>) {
        Timber.d("list size is ${list.size}")
        detailList.addAll(list)
        notifyDataSetChanged()
    }


}