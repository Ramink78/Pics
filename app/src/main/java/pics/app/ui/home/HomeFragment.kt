package pics.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.launch
import pics.app.PicsApp
import pics.app.adapters.HomeAdapter
import pics.app.data.dp
import pics.app.data.photo.model.Photo
import pics.app.ui.base.BasePhotoListFragment
import javax.inject.Inject


class HomeFragment : BasePhotoListFragment<Photo, RecyclerView.ViewHolder>() {


    private lateinit var navController: NavController

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeAdapter: HomeAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        homeViewModel.apply {
            homePhotos.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    listAdapter.submitData(it)
                }
            }
            photoClicked.observe(viewLifecycleOwner) {
                if (it != null) {
                    val action =
                        it.let { HomeFragmentDirections.actionNavigationHomeToDetailOfImage(it) }
                    navController.navigate(action)
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
        (activity?.application as PicsApp).appComponent.inject(this)


    }

    override val lManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    override val listAdapter
        get() = homeAdapter
    override val itemSpace = 24.dp()
}