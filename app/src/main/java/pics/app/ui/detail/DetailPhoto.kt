package pics.app.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import pics.app.PicsApp
import pics.app.R
import pics.app.adapters.DetailsAdapter
import pics.app.data.photo.model.Photo
import pics.app.databinding.FragmentDetailOfImageBinding
import pics.app.network.NetworkState
import pics.app.ui.base.DetailRow
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

        ((binding.detailRecyclerView.layoutManager) as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (detailadapter.getItemViewType(position)) {
                        R.layout.detail_item -> 1
                        else -> 2
                    }
                }

            }

        binding.detailRecyclerView.adapter = detailadapter
        photoViewModel.detailPhoto.observe(viewLifecycleOwner) {
            Timber.d("photo is $it")
            detailadapter.submitList(initDetails(it))



        }
        photoViewModel.networkState.observe(viewLifecycleOwner) { status ->
            when (status) {
                NetworkState.SUCCESS ->binding.detailRecyclerView.isVisible = true
            }
        }
        photoViewModel.retrieveDetails(args.photo.id)


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)
    }

    private fun initDetails(photo: Photo): List<DetailRow> {
        val detailList = arrayListOf<DetailRow>()
        detailList.apply {
            add(DetailRow.HeaderPhoto(photo))
            add(DetailRow.Separator(R.string.specfication_title))
            add(
                DetailRow.Detail(
                    photo.exif?.model ?: getString(R.string.unknown),
                    R.string.cameramodel_title
                )
            )
            add(
                DetailRow.Detail(
                    photo.exif?.focalLength ?: getString(R.string.unknown),
                    R.string.focallength_title
                )
            )
            add(
                DetailRow.Detail(
                    photo.exif?.aperture ?: getString(R.string.unknown),
                    R.string.aperture_title
                )
            )
            add(
                DetailRow.Detail(
                    photo.exif?.exposureTime ?: getString(R.string.unknown),
                    R.string.shutterspeed_title
                )
            )
            add(
                DetailRow.Detail(
                    photo.exif?.iso ?: getString(R.string.unknown),
                    R.string.iso_title
                )
            )
            add(DetailRow.Detail("${photo.width}x${photo.height}", R.string.dimensions))
        }
        return detailList
    }
}

