package pics.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pics.app.PicsApp
import pics.app.R
import pics.app.adapters.QualityAdapter
import pics.app.databinding.QualityBottomSheetBinding
import pics.app.di.ViewModelFactory
import pics.app.utils.Quality
import javax.inject.Inject

class QualityBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: QualityBottomSheetBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val homeViewModel by navGraphViewModels<HomeViewModel>(R.id.nested_nav) { viewModelFactory }


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
            homeViewModel.setQuality(it)
            dismiss()
        }
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Demo_BottomSheetDialog)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as PicsApp).appComponent.inject(this)
    }
}