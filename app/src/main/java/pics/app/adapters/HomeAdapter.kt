package pics.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pics.app.data.photo.model.Photo
import pics.app.databinding.HomeItemBinding
import pics.app.ui.home.HomeViewModel
import javax.inject.Inject

class HomeAdapter @Inject constructor(
    private val homeViewModel: HomeViewModel
) :
    PagingDataAdapter<Photo, HomeAdapter.PhotoViewHolder>(comparator) {
    private var onPhotoClickListener: OnPhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            HomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false), homeViewModel
        )


    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }


    }



    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {
        onPhotoClickListener = listener

    }


    class PhotoViewHolder(private val homeItemBinding: HomeItemBinding, private val homeViewModel: HomeViewModel) :
        RecyclerView.ViewHolder(homeItemBinding.root) {
        fun bind(photo: Photo) {
            homeItemBinding.homePhoto
            homeItemBinding.photo = photo
            homeItemBinding.viewModel = homeViewModel
            homeItemBinding.executePendingBindings()

        }
    }



    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}