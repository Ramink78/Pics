package pics.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pics.app.databinding.ListStateBinding

class ListStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ListStateAdapter.StateViewHolder>() {

    class StateViewHolder(private val binding: ListStateBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(loadState: LoadState, retry: () -> Unit) {
            binding.apply {
                val layoutParams =
                    itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                layoutParams.isFullSpan = true
                retryBtn.apply {
                    isVisible = loadState is LoadState.Error
                    setOnClickListener {
                        retry()
                    }
                }
                loading.isVisible = loadState is LoadState.Loading
            }
        }

        companion object {
            fun from(parent: ViewGroup): StateViewHolder {
                return StateViewHolder(
                    ListStateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: StateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): StateViewHolder {
        return StateViewHolder.from(parent)
    }
}