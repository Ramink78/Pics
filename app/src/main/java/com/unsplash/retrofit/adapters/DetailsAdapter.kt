package com.unsplash.retrofit.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.R
import com.unsplash.retrofit.ui.explore.DetailPhoto
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView

class DetailsAdapter(private val rows: ArrayList<DetailPhoto.Row>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val header_type: Int = 0
    val title_type: Int = 1
    val item_type: Int = 2

    /*class HeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<AspectRatioImageView>(R.id.header)
    }*/

    class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryText = itemView.findViewById<TextView>(R.id.item_primaryText)
        val secondaryText = itemView.findViewById<TextView>(R.id.item_seconderyText)
        val avatar = itemView.findViewById<ShapeableImageView>(R.id.item_avatar)
    }

    class TitleVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.details_title)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
      /*      header_type -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.headr_of_detail_image, parent, false)
                return HeaderVH(view)
            }*/
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
     //       is DetailPhoto.Row.Header -> header_type
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            /*header_type -> {
                 holder as HeaderVH
                 val header = (rows[position] as DetailPhoto.Row.Header)
                 holder.image.setAspectRatio(header.width, header.height)
                 //      ViewCompat.setTransitionName(holder.image,header.id)
                Glide.with(context)
                    .load(header.url)
                    .placeholder(ColorDrawable(Color.parseColor(header.color))).into(holder.image)

             }*/
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
   //     notifyDataSetChanged()
    }

    fun collapse(row: DetailPhoto.Row) {


    }




}