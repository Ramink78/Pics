package pics.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pics.app.databinding.DetailCategoryBinding
import pics.app.databinding.DetailChildBinding

class DetailChildViewHolder(private val binding: DetailChildBinding) :
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
            DetailChildViewHolder(
                DetailChildBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}