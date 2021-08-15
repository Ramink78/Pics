package pics.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pics.app.R
import pics.app.data.PHOTO_TYPE
import pics.app.data.TITLE_TYPE
import pics.app.data.collections.model.Collection
import pics.app.data.setAspectRatio
import pics.app.databinding.CollectionItemBinding
import pics.app.databinding.PhotoCollectionItemBinding
import pics.app.databinding.RecyclerViewHeaderBinding
import pics.app.ui.base.TitleViewHolder
import pics.app.ui.collections.CollectionsViewModel
import pics.app.ui.base.BasePhotoListAdapter
import pics.app.ui.collections.PhotoCollectionViewModel
import java.lang.ClassCastException
import javax.inject.Inject


class CollectionsAdapter @Inject constructor(
    val context: Context,
    private val viewModel: CollectionsViewModel
) :
    BasePhotoListAdapter() {
    override val title: Int
        get() = R.string.collections_label


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE_TYPE -> TitleViewHolder.from(parent)
            PHOTO_TYPE -> CollectionViewHolder.from(parent, viewModel)
            else -> throw ClassCastException("Unknown viewType: $viewType")
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind(title)
            is CollectionViewHolder -> {
                holder.apply {
                    val collection = getItem(position) as Collection
                    binding.collectionCover.setAspectRatio(
                        collection.coverPhoto.width,
                        collection.coverPhoto.height
                    )
                    bind(collection)
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

        companion object {
            fun from(parent: ViewGroup, viewModel: CollectionsViewModel): CollectionViewHolder {
                return CollectionViewHolder(
                    CollectionItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), viewModel
                )

            }
        }
    }


}

