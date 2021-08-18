package pics.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.ArrayMap
import androidx.recyclerview.widget.RecyclerView
import pics.app.databinding.DetailCategoryBinding

class DetailCategoryViewHolder(private val binding: DetailCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(separatorTitle: String) {
        binding.apply {
            setSeparatorTitle(separatorTitle)
            executePendingBindings()
        }

    }


    companion object {
        fun from(parent: ViewGroup) =
            DetailCategoryViewHolder(
                DetailCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}