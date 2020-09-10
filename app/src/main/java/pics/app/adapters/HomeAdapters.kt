package pics.app.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pics.app.R
import pics.app.data.details.model.Photo
import pics.app.data.setAspectRatio
import pics.app.network.NetworkState
import pics.app.ui.widgets.AspectRatioImageView


class HomeAdapters(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onPhotoClickListener: OnPhotoClickListener? = null
    val PROGRESS_TYPE = 0
    val PHOTO_TYPE = 1
    var networkState: NetworkState? = null

    private val differ = AsyncPagedListDiffer(this, PhotoDiffCallBack())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PROGRESS_TYPE -> LoadingVH(
                LayoutInflater.from(context).inflate(R.layout.loading, parent, false)
            )
            PHOTO_TYPE -> PhotoVH(
                LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
            )

            else -> PhotoVH(
                LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = differ.getItem(position)
        when (getItemViewType(position)) {
            PHOTO_TYPE -> (holder as PhotoVH).bind(photo!!)
            PROGRESS_TYPE -> (holder as LoadingVH).bind(networkState, position)

        }

    }


    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {

        onPhotoClickListener = listener

    }


    class PhotoDiffCallBack : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }

    fun submitList(photos: PagedList<Photo>) {
        differ.submitList(photos)
    }

    fun removeItem(position: Int) {
        differ.currentList?.removeAt(position)
    }

    override fun getItemCount(): Int {
        return differ.itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            differ.currentList?.size?.minus(1) -> {
                println("end of page")
                PROGRESS_TYPE
            }
            else -> {
                PHOTO_TYPE

            }
        }
    }

    inner class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(networkState: NetworkState?, position: Int) {
            val lparams = itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            lparams.isFullSpan = true
            when (networkState) {
                NetworkState.SUCCESS -> removeItem(position)
            }

        }


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


}