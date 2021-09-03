package pics.app.ui.collections

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pics.app.PicsApp
import pics.app.adapters.PhotoCollectionAdapter
import pics.app.data.dp
import pics.app.data.photo.model.Photo
import pics.app.di.CollectionsModule
import pics.app.ui.base.BasePhotoListFragment
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PhotosCollection : BasePhotoListFragment() {

    @Inject
    lateinit var viewModel: PhotoCollectionViewModel
    @Inject lateinit var photoCollectionAdapter: PhotoCollectionAdapter

    override val listAdapter
        get() = photoCollectionAdapter
    override val itemSpace: Int
        get() = 24.dp()
    override val spanCount: Int
        get() = 2

    private val args: PhotosCollectionArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition(280,TimeUnit.MILLISECONDS)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("viewmodel is ${viewModel.hashCode()}")

        viewModel.apply {
            photoClick.observe(viewLifecycleOwner){
                Timber.d("on observe")
                val action=
                    PhotosCollectionDirections.actionPhotosCollectionToDetailOfImage(it)
                navController.navigate(action)
            }
            collectionPhotos.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    listAdapter.submitData(it)
                }
            }

        }
        photoCollectionAdapter.apply {
            addLoadStateListener {
                when (it.refresh) {
                    LoadState.Loading -> showLoading()
                    else -> showSuccess()
                }

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.getPhotosCollectionComponent()
            .create(CollectionsModule(args.collection.id))
            .inject(this)



    }
}