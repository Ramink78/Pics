package pics.app.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import pics.app.PicsApp
import pics.app.adapters.SavedPhotoAdapter
import pics.app.data.TITLE_TYPE
import pics.app.data.dp
import pics.app.database.SavedPhoto
import pics.app.databinding.BasePhotoListFragmentBinding
import pics.app.databinding.SavedFragmentBinding
import pics.app.utils.ItemSpacing
import timber.log.Timber
import javax.inject.Inject

class SavedFragment : Fragment() {
    private var _binding: SavedFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: SavedViewModel

    @Inject
    lateinit var savedPhotoAdapter: SavedPhotoAdapter

    companion object {
        fun newInstance() = SavedFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SavedFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.savedFragmentRecyclerView.apply {
            adapter = savedPhotoAdapter
            addItemDecoration(ItemSpacing(12.dp(), 3))
        }
        viewModel.apply {
            localPhotos.observe(viewLifecycleOwner) {
                savedPhotoAdapter.addHeaderAndSubmitList(it)

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)
    }

    sealed class SavedDataItem {
        abstract val id: Int

        object Header : SavedDataItem() {
            override val id: Int
                get() = TITLE_TYPE
        }

        data class PhotoItem(val photo: SavedPhoto) : SavedDataItem() {
            override val id: Int
                get() = photo.id
        }
    }


}