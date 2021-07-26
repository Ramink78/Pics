package pics.app.ui.base

import androidx.recyclerview.widget.RecyclerView
import pics.app.databinding.RecyclerViewHeaderBinding

class TitleViewHolder(val binding: RecyclerViewHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(title: String) {
        binding.detailsTitle.text = title
    }
}


