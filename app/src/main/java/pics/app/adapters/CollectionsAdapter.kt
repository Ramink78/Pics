package pics.app.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pics.app.adapters.CollectionsAdapter.CollectionViewHolder
import pics.app.data.collections.model.Collection
import pics.app.databinding.CollectionItemBinding
import javax.inject.Inject


class CollectionsAdapter @Inject constructor(val context: Context) :
    PagingDataAdapter<Collection, CollectionViewHolder>(
        comparator
    ) {
    private var onCollectionClickListener: OnCollectionClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(
            CollectionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

    }

    override fun onBindViewHolder(holderCollection: CollectionViewHolder, position: Int) {
        val collection = getItem(position)
        if (collection != null) {
            holderCollection.bind(collection)
        }


    }

    class CollectionViewHolder(private val binding: CollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(collection: Collection) {
            binding.collection = collection
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


}

