package pics.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pics.app.R
import pics.app.data.getScreenWidth
import pics.app.data.photo.model.Photo
import pics.app.databinding.HomeItemBinding
import pics.app.databinding.RecyclerViewHeaderBinding
import pics.app.ui.base.TitleViewHolder
import pics.app.ui.home.HomeViewModel
import pics.app.uiPhoto.base.BasePhotoListAdapter
import timber.log.Timber
import javax.inject.Inject

class HomeAdapter @Inject constructor(
    private val homeViewModel: HomeViewModel
) :
    BasePhotoListAdapter<Photo, RecyclerView.ViewHolder>(PhotoDiffUtilCallBack) {
    private var onPhotoClickListener: OnPhotoClickListener? = null
    private val TITLE_TYPE = 1
    override val title: Int
        get() = R.string.home_label


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TITLE_TYPE -> {
                return TitleViewHolder(
                    RecyclerViewHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return PhotoViewHolder(
                    HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    homeViewModel
                )
            }


        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (getItemViewType(position)) {
            TITLE_TYPE -> {
                (holder as TitleViewHolder).apply {
                    val layoutParams =
                        holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                    layoutParams.isFullSpan = true
                    bind(title)
                }
            }
            else -> item?.let {
                (holder as PhotoViewHolder).apply {
                    val ratio = item.height.toDouble() / item.width.toDouble()
                  //  holder.itemView.layoutParams.height = (ratio * (getScreenWidth() / 2)).toInt()
                  //  holder.itemView.requestLayout()
                    ConstraintSet().apply {
                        clone(homeItemBinding.homeRoot)
                        setDimensionRatio(homeItemBinding.homePhoto.id,ratio.toString())
                        applyTo(homeItemBinding.homeRoot)
                    }
                    bind(it)
                }
            }
        }


    }


    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {
        onPhotoClickListener = listener

    }


    class PhotoViewHolder(
         val homeItemBinding: HomeItemBinding,
        private val homeViewModel: HomeViewModel,
    ) :
        RecyclerView.ViewHolder(homeItemBinding.root) {


        fun bind(photo: Photo) {
            homeItemBinding.photo = photo
            homeItemBinding.viewModel = homeViewModel
            homeItemBinding.executePendingBindings()

        }
    }


    object PhotoDiffUtilCallBack : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return newItem == oldItem

        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TITLE_TYPE
            else -> super.getItemViewType(position)
        }
    }


}

