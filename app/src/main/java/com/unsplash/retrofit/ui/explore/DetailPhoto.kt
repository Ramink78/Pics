package com.unsplash.retrofit.ui.explore

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.unsplash.retrofit.R
import com.unsplash.retrofit.ServiceBuilder
import com.unsplash.retrofit.adapters.DetailsAdapter
import com.unsplash.retrofit.data.details.DetailsAPI
import com.unsplash.retrofit.data.setAspectRatio
import com.unsplash.retrofit.network.NetworkState
import com.unsplash.retrofit.repo.photos.PhotoDetailsRepo
import kotlinx.android.synthetic.main.fragment_detail_of_image.*


class DetailPhoto : Fragment() {
    var lmanager: GridLayoutManager? = null
    lateinit var photoViewModel: DetailPhotoViewModel
    lateinit var detailadapter: DetailsAdapter
    lateinit var photoRepo: PhotoDetailsRepo
    private val args: DetailPhotoArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        photoRepo = PhotoDetailsRepo(ServiceBuilder.buildService(DetailsAPI::class.java))
        photoViewModel = getViewModel(args.photo.id)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition2)
       // exitTransition=TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
        return inflater.inflate(R.layout.fragment_detail_of_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailadapter = DetailsAdapter(arrayListOf(), requireContext())

        header.apply {
            ViewCompat.setTransitionName(this, args.photo.id)
            setAspectRatio(args.photo.width,args.photo.height)
            Glide.with(context)
                .load(args.photo.urls.regular)
                .placeholder(ColorDrawable(Color.parseColor(args.photo.color)))
                .thumbnail(Glide.with(context).load(args.photo.urls.small))
                .dontAnimate()
                .dontTransform()
                .into(this)


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

        photoViewModel.photoDetails.observe(viewLifecycleOwner, Observer {
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

        })
        photoViewModel.networkState.observe(viewLifecycleOwner, Observer {
            when(it){
                NetworkState.PROSSECING-> detailadapter.addItem(Row.Loading(it))
                NetworkState.SUCCESS->detailadapter.removeItem(0)
            }



        })


    }


    sealed class Row {

        data class Item(val primary: String?, val secondary: String?, val drawableRes: Int) : Row()

        //  data class Header(val url: String, val color:String,val width:Int,val height:Int,val id:String):Row()
        data class Title(val title: String) : Row()
        data class Loading(val networkState: NetworkState) : Row()


    }

    private fun getViewModel(photoId: String): DetailPhotoViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("cast eception")
                return DetailPhotoViewModel(photoRepo, photoId) as T
            }
        })[DetailPhotoViewModel::class.java]
    }


}

