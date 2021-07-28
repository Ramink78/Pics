package pics.app.ui.base

import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import pics.app.databinding.RecyclerViewHeaderBinding

class TitleViewHolder(val binding: RecyclerViewHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(@StringRes title: Int) {
        binding.detailsTitle.text = binding.root.resources.getString(title)
    }
}


