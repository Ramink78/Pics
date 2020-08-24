package com.unsplash.retrofit.ui.explore

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.unsplash.retrofit.R
import com.unsplash.retrofit.adapters.DetailsAdapter
import kotlinx.android.synthetic.main.fragment_detail_of_image.rv_details
import kotlinx.android.synthetic.main.tt.*


class DetailPhoto : Fragment() {
    var lmanager: GridLayoutManager? = null
    lateinit var photoViewModel: DetailPhotoViewModel
    lateinit var detailadapter: DetailsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photoViewModel = ViewModelProvider(this).get(DetailPhotoViewModel::class.java)
        return inflater.inflate(R.layout.tt, container, false)
    }

    private val args: DetailPhotoArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailadapter = DetailsAdapter(arrayListOf(), requireContext())

        header.apply {
            transitionName = args.photo.id
            aspectRatio = args.photo.height.toDouble() / args.photo.width.toDouble()
            Glide.with(context).load(args.photo.urls.regular)
                .placeholder(ColorDrawable(Color.parseColor(args.photo.color))).into(this)
        }

        lmanager = GridLayoutManager(requireContext(), 2)
        lmanager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (detailadapter.getItemViewType(position)) {
                    detailadapter.title_type -> 2
                    detailadapter.item_type -> 1
                    else -> 2
                }
            }

        }
        rv_details.apply {
            layoutManager = lmanager
            adapter = detailadapter

        }
        photoViewModel.result.observe(viewLifecycleOwner, Observer {
            //     detailadapter.addItem(Row.Header(it.urls.regular,it.color,it.width,it.height,it.id))
            detailadapter.addItem(Row.Title(getString(R.string.specfication_title)))
            detailadapter.addItem(
                Row.Item(
                    it.exif.model,
                    getString(R.string.cameramodel_title),
                    R.drawable.details_camera_make
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif.focalLength,
                    getString(R.string.focallength_title),
                    R.drawable.ic_detail_focal_length
                )
            )
            detailadapter.addItem(
                Row.Item(
                    "${it.exif.aperture}f",
                    getString(R.string.aperture_title),
                    R.drawable.ic_detail_aperture
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif.exposureTime,
                    getString(R.string.shutterspeed_title),
                    R.drawable.ic_detail_shutter_speed
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif.iso,
                    getString(R.string.iso_title),
                    R.drawable.ic_detail_iso
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif.iso,
                    getString(R.string.dimensions),
                    R.drawable.ic_detail_dimensions
                )
            )
            detailadapter.addItem(Row.Title(getString(R.string.aboutphotographer_title)))
            detailadapter.addItem(
                Row.Item(
                    it.user.name,
                    getString(R.string.name),
                    R.color.searchBarColor
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.location.country,
                    getString(R.string.location),
                    R.drawable.ic_location
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.user.username,
                    getString(R.string.username),
                    R.color.searchBarColor
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.user.instagramUsername,
                    getString(R.string.instagram),
                    R.drawable.ic_instagram
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.user.twitterUsername,
                    getString(R.string.twitter),
                    R.drawable.ic_twitter
                )
            )
            detailadapter.notifyItemRangeInserted(0, 13)

        })
        photoViewModel.getDetails(args.photo.id)




    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)




    }

    sealed class Row {
        data class Item(val primary: String?, val secondary: String?, val drawableRes: Int) : Row()

        //  data class Header(val url: String, val color:String,val width:Int,val height:Int,val id:String):Row()
        data class Title(val title: String) : Row()


    }


}

