package pics.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pics.app.R
import pics.app.data.collections.model.Collection
import pics.app.data.setAspectRatio
import pics.app.databinding.CollectionItemBinding
import pics.app.databinding.RecyclerViewHeaderBinding
import pics.app.ui.base.TitleViewHolder
import pics.app.ui.collections.CollectionsViewModel
import pics.app.uiPhoto.base.BasePhotoListAdapter
import javax.inject.Inject


class CollectionsAdapter @Inject constructor(
    val context: Context,
    private val viewModel: CollectionsViewModel
) :
    BasePhotoListAdapter<Collection, RecyclerView.ViewHolder>(
        comparator
    ) {
    override val title: Int
        get() = R.string.collections_label
    val TITLE_TYPE = 1;
    private lateinit var onCollectionClickListener: OnCollectionClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE_TYPE -> TitleViewHolder(
                RecyclerViewHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> CollectionViewHolder(
                CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                viewModel
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val collection = getItem(position)
        when (getItemViewType(position)) {
            TITLE_TYPE -> {
                (holder as TitleViewHolder).bind(title)

            }
            else -> {
                if (collection != null) {
                    (holder as CollectionViewHolder).apply {
                        binding.collectionCover.setAspectRatio(
                            collection.coverPhoto.width,
                            collection.coverPhoto.height
                        )
                        bind(collection)
                    }
                }
            }
        }


    }

    class CollectionViewHolder(
        val binding: CollectionItemBinding,
        private val viewModel: CollectionsViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(collection: Collection) {
            binding.collection = collection
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }


    fun setOnPhotoClickListener(listener: OnCollectionClickListener) {
        onCollectionClickListener = listener
    }


    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Collection>() {
            override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean =
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

