package com.unsplash.retrofit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.unsplash.retrofit.R
import com.unsplash.retrofit.data.details.Detail
import com.unsplash.retrofit.data.details.Details

class DetailsAdapter(val detail: Detail) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER_TYPE: Int = 1

    //val TITLE_TYPE: Int = 2
    val SPECIFICATIONS_TYPE: Int = 2
    val DOWNLOADS_TYPE: Int = 4
    val PHOTOGRAPHER_TYPE: Int = 3
    lateinit var view: View
    lateinit var detailsH: SpecH
    lateinit var headerH: HeaderH
    lateinit var photographerH: PhotographerH

    class HeaderH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.header)

    }


    class SpecH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val title = itemView.findViewById<TextView>(R.id.details_title)
        val focalLength: TextView = itemView.findViewById(R.id.focalPrimaryText)
        val shutterSpeed: TextView = itemView.findViewById(R.id.shutterPrimaryText)
        val cameraMake: TextView = itemView.findViewById(R.id.cameraMakePrimaryText)
        val aperture: TextView = itemView.findViewById(R.id.aperturePrimaryText)
        val views = itemView.findViewById<TextView>(R.id.viewsPrimaryText)
        val downloads = itemView.findViewById<TextView>(R.id.downloadsPrimaryText)
        val dimentions: TextView = itemView.findViewById(R.id.dimensionsPrimaryText)
        val iso: TextView = itemView.findViewById(R.id.isoPrimaryText)
        val title: ConstraintLayout = itemView.findViewById(R.id.title_detail)

        val expandedLayout: ConstraintLayout = itemView.findViewById(R.id.ExpandedSpecifications)


        // val avatar = itemView.findViewById<ShapeableImageView>(R.id.avatarDetails)
    }

    class PhotographerH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val title = itemView.findViewById<TextView>(R.id.details_title)
        val name: TextView = itemView.findViewById(R.id.pName)
        val location: TextView = itemView.findViewById(R.id.pLocation)
        val twitter: TextView = itemView.findViewById(R.id.pTwitter)
        val instagram: TextView = itemView.findViewById(R.id.pInstagram)
        val bio = itemView.findViewById<TextView>(R.id.pBio)


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
            SPECIFICATIONS_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.specifications, parent, false)
                detailsH = SpecH(view)

                return detailsH
            }
            /* DOWNLOADS_TYPE -> {
                 view = LayoutInflater.from(parent.context)
                     .inflate(R.layout.downloads, parent, false)
                 detailsH = SpecH(view)
                 return detailsH
             }*/
            PHOTOGRAPHER_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.about_photographer, parent, false)
                photographerH = PhotographerH(view)
                return photographerH
            }

        }
        return SpecH(view)


    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> {
                return HEADER_TYPE
            }
            1 -> {
                return SPECIFICATIONS_TYPE
            }
            2 -> {
                return PHOTOGRAPHER_TYPE
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
            PHOTOGRAPHER_TYPE -> {
                photographerH.name.text = detail.user.firstName
                photographerH.location.text = detail.user.location
                photographerH.twitter.text = detail.user.twitterUsername
                photographerH.instagram.text = detail.user.instagramUsername
                photographerH.bio.text = detail.user.bio
            }

            SPECIFICATIONS_TYPE -> {
                detailsH.cameraMake.text = detail.exif.model
                detailsH.focalLength.text = detail.exif.focalLength
                detailsH.shutterSpeed.text = detail.exif.exposureTime
                detailsH.aperture.text = detail.exif.aperture
                detailsH.iso.text = detail.exif.iso
                detailsH.dimentions.text = "${detail.width} x ${detail.height}"
                if (detail.collapsed) {
                    detailsH.expandedLayout.visibility = View.GONE
                } else {
                    detailsH.expandedLayout.visibility = View.VISIBLE
                }

                detailsH.title.setOnClickListener {
                    detail.collapsed = !detail.collapsed
//                    detailsH.expandedLayout.visibility = View.GONE
                    notifyItemChanged(1)
                }

            }
        }
    }


}