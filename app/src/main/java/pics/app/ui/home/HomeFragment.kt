package pics.app.ui.home

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import kotlinx.coroutines.launch
import pics.app.PicsApp
import pics.app.R
import pics.app.adapters.HomeAdapter
import pics.app.data.dp
import pics.app.data.photo.model.Photo
import pics.app.di.ViewModelFactory
import pics.app.ui.base.BasePhotoListFragment
import javax.inject.Inject


class HomeFragment : BasePhotoListFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val homeViewModel by navGraphViewModels<HomeViewModel>(R.id.nested_nav) { viewModelFactory }
    lateinit var photoToDownload: Photo
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(homeViewModel)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.apply {

            permissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                    ?: isWritePermissionGranted
                isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                    ?: isWritePermissionGranted
            }
            checkReadWritePermission()

            homePhotos.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    listAdapter.submitData(it)
                }

            }
            photoClicked.observe(viewLifecycleOwner) {

                val action =
                    it.let { HomeFragmentDirections.actionNavigationHomeToDetailOfImage(it) }
                navController.navigate(action)
            }
            downloadAction.observe(viewLifecycleOwner) {
                photoToDownload = it
                val action =
                    it.let { HomeFragmentDirections.actionNavigationHomeToQualityBottomSheet(it) }
                navController.navigate(action)
            }
            qualityLiveData.observe(viewLifecycleOwner) {
                beginDownload(photoToDownload, it)
            }
        }

        homeAdapter.apply {
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
        (activity?.application as PicsApp).appComponent.inject(this)


    }


    override val listAdapter
        get() = homeAdapter
    override val itemSpace = 24.dp()
    override val spanCount: Int
        get() = 2
}
