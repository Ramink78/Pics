package com.unsplash.retrofit.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.unsplash.retrofit.R
import com.unsplash.retrofit.adapters.DetailsAdapter
import com.unsplash.retrofit.data.random.Exif
import kotlinx.android.synthetic.main.fragment_detail_of_image.*


class DetailPhoto : Fragment() {
    var lmanager: GridLayoutManager? = null
    lateinit var photoViewModel: DetailPhotoViewModel
    var detailadapter=DetailsAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photoViewModel= ViewModelProviders.of(this).get(DetailPhotoViewModel::class.java)
        return inflater.inflate(R.layout.fragment_detail_of_image, container, false)
    }

    val args: DetailPhotoArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSharedElementTransitionOnEnter()
        postponeEnterTransition()
        lmanager = GridLayoutManager(requireContext(),2)
        lmanager!!.spanSizeLookup= object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return when(detailadapter.getItemViewType(position)){
                     detailadapter.header_type->2
                    detailadapter.title_type->2
                    detailadapter.item_type->1
                    else -> 2
                }
            }

        }
        rv_details.apply {
            layoutManager = lmanager
            adapter=detailadapter
        }



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        photoViewModel.result.observe(viewLifecycleOwner, Observer {
            detailadapter.addItem(Row.Header(it.urls.regular,it.color,it.width,it.height))
            detailadapter.addItem(Row.Title(getString(R.string.specfication_title)))
            detailadapter.addItem(Row.Item(it.exif.model,getString(R.string.cameramodel_title),R.drawable.details_camera_make))
            detailadapter.addItem(Row.Item(it.exif.focalLength,getString(R.string.focallength_title),R.drawable.ic_detail_focal_length))
            detailadapter.addItem(Row.Item("${it.exif.aperture}f",getString(R.string.aperture_title),R.drawable.ic_detail_aperture))
            detailadapter.addItem(Row.Item(it.exif.exposureTime,getString(R.string.shutterspeed_title),R.drawable.ic_detail_shutter_speed))
            detailadapter.addItem(Row.Item(it.exif.iso,getString(R.string.iso_title),R.drawable.ic_detail_iso))
            detailadapter.addItem(Row.Item(it.exif.iso,getString(R.string.dimensions),R.drawable.ic_detail_dimensions))
            detailadapter.addItem(Row.Title(getString(R.string.aboutphotographer_title)))
            detailadapter.addItem(Row.Item(it.user.name,getString(R.string.name),R.color.searchBarColor))
            detailadapter.addItem(Row.Item(it.location.country,getString(R.string.location),R.drawable.ic_location))
            detailadapter.addItem(Row.Item(it.user.username,getString(R.string.username),R.color.searchBarColor))
            detailadapter.addItem(Row.Item(it.user.instagramUsername,getString(R.string.instagram),R.drawable.ic_instagram))
            detailadapter.addItem(Row.Item(it.user.twitterUsername,getString(R.string.twitter),R.drawable.ic_twitter))

        })
        photoViewModel.getDetails(args.id)

    }



    sealed class Row{
        data class Item(val primary:String?, val secondary:String?, val drawableRes: Int):Row()
        data class Header(val url: String, val color:String,val width:Int,val height:Int):Row()
        data class Title(val title:String):Row()

    }
    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }
}