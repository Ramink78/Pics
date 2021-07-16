package pics.app.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pics.app.R
import pics.app.data.photo.model.Photo
import pics.app.data.setAspectRatio
import pics.app.ui.widgets.AspectRatioImageView


class HomeAdapters(val context: Context) :
    PagingDataAdapter<Photo, HomeAdapters.PhotoVH>(comparator) {
    private var onPhotoClickListener: OnPhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapters.PhotoVH {
        return PhotoVH(
            LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
        )


    }

    override fun onBindViewHolder(holder: HomeAdapters.PhotoVH, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }


    }


    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {
        onPhotoClickListener = listener

    }


    inner class PhotoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: AspectRatioImageView = itemView.findViewById(R.id.cePic)
        fun bind(photo: Photo) {
            image.apply {
                ViewCompat.setTransitionName(this, photo.id)
                // this.transitionName = photo.id
                setAspectRatio(photo.width, photo.height)
                Glide.with(context)
                    .load(photo.urls.small)
                    .placeholder(ColorDrawable(Color.parseColor(photo.color)))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(this)
                setOnClickListener {
                    onPhotoClickListener?.onClick(photo.id, adapterPosition, it, photo)

                }
            }
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


}