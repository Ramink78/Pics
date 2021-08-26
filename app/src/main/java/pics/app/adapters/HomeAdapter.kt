package pics.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.home_item.view.*
import pics.app.R
import pics.app.data.PHOTO_TYPE
import pics.app.data.TITLE_TYPE
import pics.app.data.photo.model.Photo
import pics.app.databinding.HomeItemBinding
import pics.app.ui.base.BasePhotoListAdapter
import pics.app.ui.base.TitleViewHolder
import pics.app.ui.home.HomeViewModel
import javax.inject.Inject

class HomeAdapter(
    private val viewModel: HomeViewModel
) :
    BasePhotoListAdapter() {
    override val title: Int
        get() = R.string.home_label
    var progress = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE_TYPE -> TitleViewHolder.from(parent)
            PHOTO_TYPE -> PhotoViewHolder.from(parent, viewModel)
            else -> throw ClassCastException("Unknown viewType: $viewType")
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                holder.apply {
                    val layoutParams =
                        holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                    layoutParams.isFullSpan = true
                    bind(title)
                }
            }
            is PhotoViewHolder ->
                holder.apply {
                    val photo = getItem(position) as Photo
                    val ratio = photo.width.toDouble() / photo.height.toDouble()
                    ConstraintSet().apply {
                        val root = itemView.rootView as ConstraintLayout
                        clone(root)
                        setDimensionRatio(itemView.home_photo.id, ratio.toString())
                        applyTo(root)
                    }
                    bind(photo)
                }

        }


    }


    class PhotoViewHolder(
        private val binding: HomeItemBinding,
        private val viewModel: HomeViewModel,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo?) {

            binding.photo = photo
            binding.viewModel = viewModel
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup, viewModel: HomeViewModel): PhotoViewHolder {
                return PhotoViewHolder(
                    HomeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), viewModel
                )

            }
        }
    }


}

