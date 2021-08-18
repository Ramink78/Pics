package pics.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pics.app.databinding.QualityItemBinding
import pics.app.utils.Quality

class QualityAdapter(private val onClick: (q: Quality) -> Unit) :
    ListAdapter<Quality, QualityAdapter.QualityViewHolder>(comparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QualityViewHolder {
        return QualityViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: QualityViewHolder, position: Int) {
        holder.apply {
            bind(getItem(position)) {
                onClick.invoke(it)

            }
        }
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<Quality>() {
            override fun areItemsTheSame(oldItem: Quality, newItem: Quality): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Quality, newItem: Quality): Boolean {
                return newItem == oldItem
            }

        }
    }

    class QualityViewHolder(
        private val binding: QualityItemBinding,

        ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quality: Quality, onClick: (q: Quality) -> Unit) {
            binding.qualityNameTxt.text = quality.title
            // binding.qualityImageSize.text = "${Random.nextDouble(15.0)} MB"
            itemView.setOnClickListener {
                onClick(quality)

            }
        }


        companion object {
            fun from(parent: ViewGroup) =
                QualityViewHolder(
                    QualityItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }
}