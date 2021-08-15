package pics.app.ui.home

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pics.app.PicsApp
import pics.app.adapters.HomeAdapter
import pics.app.data.dp
import pics.app.data.photo.model.Photo
import pics.app.ui.base.BasePhotoListFragment
import javax.inject.Inject


class HomeFragment : BasePhotoListFragment() {


    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeAdapter: HomeAdapter


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
        (activity?.application as PicsApp).appComponent.inject(this)


    }



    override val listAdapter
        get() = homeAdapter
    override val itemSpace = 24.dp()
    override val spanCount: Int
        get() = 2
}
