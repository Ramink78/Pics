package com.unsplash.retrofit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.details.Detail
import com.unsplash.retrofit.data.details.Details

class DetailsAdapter(val detail: Detail) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER_TYPE: Int = 1
    val TITLE_TYPE: Int = 2
    val DETAILS_TYPE: Int = 3
    lateinit var view: View
    lateinit var detailsH: DetailsH
    lateinit var headerH: HeaderH

    class HeaderH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ShapeableImageView>(R.id.header)

    }

    class DetailsH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val title = itemView.findViewById<TextView>(R.id.details_title)
        val focalLength = itemView.findViewById<TextView>(R.id.focalPrimaryText)
        val shutterSpeed = itemView.findViewById<TextView>(R.id.shutterPrimaryText)
        val cameraMake = itemView.findViewById<TextView>(R.id.cameraMakePrimaryText)
        val aperture = itemView.findViewById<TextView>(R.id.aperturePrimaryText)
        val views = itemView.findViewById<TextView>(R.id.viewsPrimaryText)
        val downloads = itemView.findViewById<TextView>(R.id.downloadsPrimaryText)
        val dimentions = itemView.findViewById<TextView>(R.id.dimensionsPrimaryText)
        val iso = itemView.findViewById<TextView>(R.id.isoPrimaryText)
        // val avatar = itemView.findViewById<ShapeableImageView>(R.id.avatarDetails)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HEADER_TYPE -> {

                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.headr_of_detail_image, parent, false)
                headerH = HeaderH(view)
                return headerH
            }


            DETAILS_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.specifications, parent, false)
                detailsH = DetailsH(view)
                return detailsH
            }

        }
        return DetailsH(view)


    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> {
                return HEADER_TYPE
            }
            1 -> {
                return DETAILS_TYPE
            }
        }
        return -1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HEADER_TYPE -> {
                headerH.image.setBackgroundColor(Color.parseColor(detail.color))
                Picasso.get().load(detail.urls.regular)
                    .into(headerH.image)
            }
            DETAILS_TYPE -> {
                detailsH.cameraMake.text = detail.exif.model
                detailsH.focalLength.text = detail.exif.focalLength
                detailsH.shutterSpeed.text = detail.exif.exposureTime
                detailsH.aperture.text = detail.exif.aperture
                detailsH.iso.text = detail.exif.iso
                detailsH.dimentions.text = "${detail.width} x ${detail.height}"
                //   detailsH.aperture.text = detail.exif.aperture
            }
        }
    }


}