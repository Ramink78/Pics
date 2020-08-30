package com.unsplash.retrofit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.unsplash.retrofit.R
import com.unsplash.retrofit.ui.explore.DetailPhoto

class DetailsAdapter(private val rows: ArrayList<DetailPhoto.Row>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val title_type: Int = 1
    val item_type: Int = 2

    class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText: TextView = itemView.findViewById(R.id.item_primaryText)
        val secondaryText: TextView = itemView.findViewById(R.id.item_seconderyText)
        val avatar: ShapeableImageView = itemView.findViewById(R.id.item_avatar)
    }

    class TitleVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.details_title)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {

            item_type -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.detail_item, parent, false)

                return ItemVH(view)
            }
            title_type -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.detail_title, parent, false)
                return TitleVH(view)
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
            is DetailPhoto.Row.Title -> title_type
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            item_type -> {
                val item = rows[position] as DetailPhoto.Row.Item
                (holder as ItemVH).primaryText.text = item.primary
                holder.secondaryText.text = item.secondary
                holder.avatar.setImageResource(item.drawableRes)
            }
            title_type -> {
                val title = (rows[position] as DetailPhoto.Row.Title).title
                holder as TitleVH
                holder.title.text = title
            }
        }
    }

    fun addItem(row: DetailPhoto.Row) {
        rows.add(row)
        notifyDataSetChanged()
    }

}