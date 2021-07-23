package pics.app.ui.explore

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import pics.app.PicsApp
import pics.app.R
import pics.app.adapters.DetailsAdapter
import pics.app.databinding.FragmentDetailOfImageBinding
import pics.app.network.NetworkState
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject


class DetailPhoto : Fragment() {
    var lmanager: GridLayoutManager? = null

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var photoViewModel: DetailPhotoViewModel

    @Inject
    lateinit var detailadapter: DetailsAdapter
    private lateinit var binding: FragmentDetailOfImageBinding
    private val args: DetailPhotoArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailOfImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photo = args.photo
        lmanager = GridLayoutManager(requireContext(), 2)
        binding.rvDetails.apply {
            layoutManager = lmanager
            adapter = detailadapter


        }
        lmanager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (detailadapter.getItemViewType(position)) {
                    detailadapter.title_type -> 2
                    detailadapter.item_type -> 1
                    else -> 2
                }
            }

        }
        photoViewModel.retrieveDetails(args.photo.id)

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


    }


    sealed class Row {
        data class Item(val primary: String?, val secondary: String, val drawableRes: Int) : Row()
        data class Section(val title: String) : Row()
        data class Loading(val networkState: NetworkState) : Row()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)
    }
}
