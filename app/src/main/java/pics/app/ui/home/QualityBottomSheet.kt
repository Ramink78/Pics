package pics.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pics.app.adapters.QualityAdapter
import pics.app.data.*
import pics.app.data.photo.model.Photo
import pics.app.database.SavePhotoWorker
import pics.app.databinding.QualityBottomSheetBinding
import pics.app.network.DownloadPhotoWorker
import pics.app.utils.Quality
import pics.app.utils.getImageUrl
import timber.log.Timber

class QualityBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: QualityBottomSheetBinding
    lateinit var workManager: WorkManager
    private val args: QualityBottomSheetArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = QualityBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val qualityAdapter = QualityAdapter {
            Timber.d("clicked on ${it.name}")
            beginDownload(args.photo,it)
            dismiss()
        }
        workManager = WorkManager.getInstance(requireContext())
        binding.apply {
            bottomSheetRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = qualityAdapter
            }
        }
        val qualityList = mutableListOf<Quality>()

        qualityList.add(Quality.RAW)
        qualityList.add(Quality.FULL)
        qualityList.add(Quality.REGULAR)
        qualityList.add(Quality.SMALL)
        qualityAdapter.submitList(qualityList)
    }

    private fun beginDownload(photo: Photo, quality: Quality): Boolean {
        val inputData = workDataOf(
            KEY_IMAGE_ID to photo.id,
            KEY_IMAGE_WIDTH to photo.width,
            KEY_IMAGE_HEIGHT to photo.height,
            KEY_IMAGE_COLOR to photo.color,
            KEY_IMAGE_URL to getImageUrl(
                photo, quality
            ),
            KEY_IMAGE_THUMBNAIL_URL to photo.urls.small
        )
        var continuation =
            workManager.beginWith(
                OneTimeWorkRequestBuilder<DownloadPhotoWorker>()
                    .setInputData(inputData)
                    .build()
            )
        val saveToDatabaseRequest = OneTimeWorkRequest.from(SavePhotoWorker::class.java)

        continuation = continuation.then(saveToDatabaseRequest)
        continuation.enqueue()

        return true
    }
}