package pics.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pics.app.databinding.DetailItemBinding

class DetailItemViewHolder(private val binding: DetailItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(primaryText: String, secondaryText: String) {
        binding.apply {
            setPrimaryText(primaryText)
            setSecondaryText(secondaryText)
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup) =
            DetailItemViewHolder(
                DetailItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}