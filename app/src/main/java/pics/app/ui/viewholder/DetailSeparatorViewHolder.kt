package pics.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pics.app.databinding.DetailItemBinding
import pics.app.databinding.DetailSeparatorBinding

class DetailSeparatorViewHolder(private val binding: DetailSeparatorBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(separatorTitle: String) {
        binding.apply {
            setSeparatorTitle(separatorTitle)
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup) =
            DetailSeparatorViewHolder(
                DetailSeparatorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}