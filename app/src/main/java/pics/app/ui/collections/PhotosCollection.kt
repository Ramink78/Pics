package pics.app.ui.collections

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
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
import javax.inject.Inject

class PhotosCollection : BasePhotoListFragment<Photo, RecyclerView.ViewHolder>() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            collectionPhotos.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    listAdapter.submitData(it)
                }
            }
        }
        listAdapter.apply {
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