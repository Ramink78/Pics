package pics.app.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import pics.app.R
import pics.app.data.details.model.Photo


class ExploreAdapter(val context: Context) : RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {
    private var onPhotoClickListener: OnPhotoClickListener? = null


    val differ = AsyncPagedListDiffer(this, ExploreDiffCallback())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.explore_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return differ.itemCount
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo=differ.getItem(position)
        holder.image.transitionName = photo!!.id
        Glide.with(context).load(photo.urls.small)
            .placeholder(ColorDrawable(Color.parseColor(photo.color)))
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.image)



        holder.image.setOnClickListener {
            onPhotoClickListener?.onClick(photo.id, position, it, photo)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.ePic)

    }

    fun submitList(photos: PagedList<Photo>) {
      differ.submitList(photos)
    }


    fun setOnPhotoClickListener(listener: OnPhotoClickListener) {
        onPhotoClickListener = listener
    }


    class ExploreDiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }


}

