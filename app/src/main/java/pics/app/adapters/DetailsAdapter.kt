package pics.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import pics.app.R
import pics.app.network.NetworkState
import pics.app.ui.explore.DetailPhoto

class DetailsAdapter(private val rows: ArrayList<DetailPhoto.Row>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val title_type: Int = 1
    val item_type: Int = 2
    val loading_type: Int = 3
    lateinit var networkState: NetworkState

    class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText: TextView = itemView.findViewById(R.id.item_primaryText)
        val secondaryText: TextView = itemView.findViewById(R.id.item_seconderyText)
        val avatar: ShapeableImageView = itemView.findViewById(R.id.item_avatar)
    }

    class TitleVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.details_title)
    }

    class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            item_type -> {
                ItemVH(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.detail_item, parent, false)
                )
            }
            title_type -> {
                TitleVH(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.detail_title, parent, false)
                )
            }
            loading_type -> {
                LoadingVH(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.loading, parent, false)
                )
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.detail_title, parent, false)
                return TitleVH(view)
            }

        }


    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (rows[position]) {
            is DetailPhoto.Row.Item -> item_type
            is DetailPhoto.Row.Section -> title_type
            is DetailPhoto.Row.Loading -> loading_type
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            item_type -> {
                val item = rows[position] as DetailPhoto.Row.Item
                (holder as ItemVH).primaryText.text = if (item.primary.isNullOrEmpty()) "Unknown" else item.primary

                holder.secondaryText.text = item.secondary
                holder.avatar.setImageResource(item.drawableRes)
            }
            title_type -> {
                val title = (rows[position] as DetailPhoto.Row.Section).title
                holder as TitleVH
                holder.title.text = title
            }


        }
    }

    fun addItem(row: DetailPhoto.Row) {
        rows.add(row)
        notifyDataSetChanged()
    }
    fun removeItem(position: Int){
        rows.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setNetworkStatus(networkState: NetworkState) {
        this.networkState = networkState
    }

}