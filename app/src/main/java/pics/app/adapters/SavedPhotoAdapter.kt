package pics.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pics.app.R
import pics.app.data.PHOTO_TYPE
import pics.app.data.TITLE_TYPE
import pics.app.database.SavedPhoto
import pics.app.databinding.RecyclerViewHeaderBinding
import pics.app.databinding.SavedItemBinding
import pics.app.ui.SavedFragment.SavedDataItem
import pics.app.ui.base.TitleViewHolder
import timber.log.Timber
import java.lang.ClassCastException
import javax.inject.Inject

class SavedPhotoAdapter @Inject constructor() :
    ListAdapter<SavedDataItem, RecyclerView.ViewHolder>(differ) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    companion object {
        private val differ = object : DiffUtil.ItemCallback<SavedDataItem>() {
            override fun areItemsTheSame(
                oldItem: SavedDataItem,
                newItem: SavedDataItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SavedDataItem,
                newItem: SavedDataItem
            ): Boolean {
                return newItem == oldItem

            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                holder.apply {
                    val layoutParams =
                        holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                    layoutParams.isFullSpan = true
                    bind(R.string.saved_label)
                }
            }
            is SavedPhotoViewHolder -> {
                val photoItem = (getItem(position) as SavedDataItem.PhotoItem).photo
                holder.apply {
                    val ratio = photoItem.height.toDouble() / photoItem.width.toDouble()
                    //  holder.itemView.layoutParams.height = (ratio * (getScreenWidth() / 2)).toInt()
                    //  holder.itemView.requestLayout()
                    ConstraintSet().apply {
                        clone(binding.savedRoot)
                        setDimensionRatio(binding.homePhoto.id, ratio.toString())
                        applyTo(binding.savedRoot)
                    }
                    bind(photoItem)
                }


            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TITLE_TYPE -> {
                return TitleViewHolder.from(parent)
            }
            PHOTO_TYPE ->
                SavedPhotoViewHolder.from(parent)
            else -> throw ClassCastException("unknown viewType $viewType")
        }


    }

    class SavedPhotoViewHolder(val binding: SavedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(savedPhoto: SavedPhoto) {
            binding.savedPhoto = savedPhoto
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SavedPhotoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SavedItemBinding.inflate(inflater, parent, false)
                return SavedPhotoViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SavedDataItem.Header -> TITLE_TYPE
            is SavedDataItem.PhotoItem -> PHOTO_TYPE
        }
    }

    fun addHeaderAndSubmitList(list: List<SavedPhoto>) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(SavedDataItem.Header)
                else -> listOf(SavedDataItem.Header) + list.map { SavedDataItem.PhotoItem(it) }

            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

}