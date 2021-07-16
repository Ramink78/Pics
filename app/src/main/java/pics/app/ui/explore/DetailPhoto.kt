package pics.app.ui.explore

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_of_image.*
import pics.app.PicsApp
import pics.app.R
import pics.app.adapters.DetailsAdapter
import pics.app.data.setAspectRatio
import pics.app.network.NetworkState
import retrofit2.Retrofit
import javax.inject.Inject


class DetailPhoto : Fragment() {
    var lmanager: GridLayoutManager? = null

    @Inject
    lateinit var photoViewModel: DetailPhotoViewModel
    lateinit var detailadapter: DetailsAdapter
    private val args: DetailPhotoArgs by navArgs()

    @Inject
    lateinit var retrofit: Retrofit
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            setAspectRatio(args.photo.width, args.photo.height)
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
        photoViewModel.detailPhoto.observe(viewLifecycleOwner) {
            detailadapter.addItem(Row.Section(getString(R.string.specfication_title)))
            detailadapter.addItem(
                Row.Item(
                    it.exif?.model,
                    getString(R.string.cameramodel_title),
                    R.drawable.details_camera_make
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif?.focalLength,
                    getString(R.string.focallength_title),
                    R.drawable.ic_detail_focal_length
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif?.aperture,
                    getString(R.string.aperture_title),
                    R.drawable.ic_detail_aperture
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif?.exposureTime,
                    getString(R.string.shutterspeed_title),
                    R.drawable.ic_detail_shutter_speed
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif?.iso,
                    getString(R.string.iso_title),
                    R.drawable.ic_detail_iso
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.exif?.iso,
                    getString(R.string.dimensions),
                    R.drawable.ic_detail_dimensions
                )
            )
            detailadapter.addItem(Row.Section(getString(R.string.aboutphotographer_title)))
            detailadapter.addItem(
                Row.Item(
                    it.user?.name,
                    getString(R.string.name),
                    R.color.searchBarColor
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.location?.country,
                    getString(R.string.location),
                    R.drawable.ic_location
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.user?.username,
                    getString(R.string.username),
                    R.color.searchBarColor
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.user?.instagram_username,
                    getString(R.string.instagram),
                    R.drawable.ic_instagram
                )
            )
            detailadapter.addItem(
                Row.Item(
                    it.user?.twitter_username,
                    getString(R.string.twitter),
                    R.drawable.ic_twitter
                )
            )

        }
        photoViewModel.networkState.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.PROCESSING -> detailadapter.addItem(Row.Loading(it))
                NetworkState.SUCCESS -> detailadapter.removeItem(0)
            }


        })
        photoViewModel.retrieveDetails(args.photo.id)


    }


    sealed class Row {
        data class Item(val primary: String?, val secondary: String, val drawableRes: Int) : Row()

        //  data class Header(val url: String, val color:String,val width:Int,val height:Int,val id:String):Row()
        data class Section(val title: String) : Row()
        data class Loading(val networkState: NetworkState) : Row()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)
    }
}

