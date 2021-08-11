package pics.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pics.app.R
import pics.app.data.TITLE_TYPE
import pics.app.data.getScreenWidth
import pics.app.data.photo.model.Photo
import pics.app.databinding.PhotoCollectionItemBinding
import pics.app.databinding.RecyclerViewHeaderBinding
import pics.app.ui.base.TitleViewHolder
import pics.app.ui.collections.PhotoCollectionViewModel
import pics.app.uiPhoto.base.BasePhotoListAdapter
import timber.log.Timber
import javax.inject.Inject

class PhotoCollectionAdapter @Inject constructor(
    private val viewModel: PhotoCollectionViewModel
) :
    BasePhotoListAdapter<Photo, RecyclerView.ViewHolder>(comparator) {
    private var onPhotoClickListener: OnPhotoClickListener? = null

    override val title: Int
        get() = R.string.photos_collection_label


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE_TYPE -> TitleViewHolder(
                RecyclerViewHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> PhotoViewHolder(
                PhotoCollectionItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                viewModel
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = getItem(position)
        when (getItemViewType(position)) {
            TITLE_TYPE -> (holder as TitleViewHolder).apply {
                val layoutParams =
                    holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                layoutParams.isFullSpan = true
                bind(title)
            }
            else -> photo?.let {
                (holder as PhotoViewHolder).apply {
                    val ratio = photo.height.toDouble() / photo.width.toDouble()
                    holder.itemView.layoutParams.height = (ratio * (getScreenWidth() / 2)).toInt()
                    holder.itemView.requestLayout()
                    bind(it)
                }
            }
        }


    }


    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {
        onPhotoClickListener = listener

    }


    class PhotoViewHolder(
        private val homeItemBinding: PhotoCollectionItemBinding,
        private val viewModel: PhotoCollectionViewModel,
    ) :
        RecyclerView.ViewHolder(homeItemBinding.root) {


        fun bind(photo: Photo) {
            Timber.d(" photo is ${photo.urls}")
            homeItemBinding.photo = photo
            homeItemBinding.viewModel = viewModel
            homeItemBinding.executePendingBindings()

        }
    }


    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TITLE_TYPE
            else -> super.getItemViewType(position)
        }
    }


}