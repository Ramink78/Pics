package pics.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pics.app.data.collections.model.Collection
import pics.app.databinding.CollectionItemBinding
import pics.app.ui.base.BasePhotoListAdapter
import javax.inject.Inject


class CollectionsAdapter @Inject constructor(val context: Context) :
    BasePhotoListAdapter<Collection, RecyclerView.ViewHolder>(
        comparator
    ) {
    private var onCollectionClickListener: OnCollectionClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CollectionViewHolder(
            CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holderCollection: RecyclerView.ViewHolder, position: Int) {
        val collection = getItem(position)
        if (collection != null) {
            (holderCollection as CollectionViewHolder).bind(collection)
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

