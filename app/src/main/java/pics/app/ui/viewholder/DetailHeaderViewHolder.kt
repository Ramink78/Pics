package pics.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pics.app.data.photo.model.Photo
import pics.app.databinding.DetailHeaderBinding
import timber.log.Timber

class DetailHeaderViewHolder(private val binding: DetailHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo) {
        Timber.d("photo is $photo")
        binding.apply {
            setPhoto(photo)
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup) =
            DetailHeaderViewHolder(
                DetailHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}