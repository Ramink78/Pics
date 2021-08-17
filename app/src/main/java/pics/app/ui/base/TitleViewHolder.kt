package pics.app.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import pics.app.databinding.RecyclerViewHeaderBinding

class TitleViewHolder(val binding: RecyclerViewHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(@StringRes title: Int) {
        binding.separatorTxt.text = binding.root.resources.getString(title)
    }

    companion object {
        fun from(parent: ViewGroup): TitleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerViewHeaderBinding.inflate(inflater, parent, false)
            return TitleViewHolder(binding)
        }
    }
}


