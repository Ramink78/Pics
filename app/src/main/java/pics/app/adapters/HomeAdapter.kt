package pics.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pics.app.R
import pics.app.data.PHOTO_TYPE
import pics.app.data.TITLE_TYPE
import pics.app.data.photo.model.Photo
import pics.app.databinding.HomeItemBinding
import pics.app.ui.base.TitleViewHolder
import pics.app.ui.home.HomeViewModel
import pics.app.ui.base.BasePhotoListAdapter
import timber.log.Timber
import javax.inject.Inject

class HomeAdapter @Inject constructor(
    private val viewModel: HomeViewModel
) :
    BasePhotoListAdapter() {
    override val title: Int
        get() = R.string.home_label


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
                    Timber.d("position is $position")
                    val photo = getItem(position) as Photo
                    val ratio = photo.height.toDouble() / photo.width.toDouble()
                    //  holder.itemView.layoutParams.height = (ratio * (getScreenWidth() / 2)).toInt()
                    //  holder.itemView.requestLayout()
                    ConstraintSet().apply {
                        clone(binding.homeRoot)
                        setDimensionRatio(binding.homePhoto.id, ratio.toString())
                        applyTo(binding.homeRoot)
                    }
                    bind(photo)
                }

        }


    }


    class PhotoViewHolder(
        val binding: HomeItemBinding,
        private val viewModel: HomeViewModel,
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(photo: Photo) {
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

