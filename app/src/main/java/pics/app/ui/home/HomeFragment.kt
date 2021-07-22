package pics.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.LoadState
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import pics.app.PicsApp
import pics.app.R
import pics.app.adapters.HomeAdapter
import pics.app.data.dp
import pics.app.utils.ItemSpacing
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var homeAdapter: HomeAdapter
    private lateinit var navController: NavController

    @Inject
    lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        home_recycler_view.apply {

            addItemDecoration(ItemSpacing(24.dp(), 2))
            adapter = homeAdapter

        }
        homeViewModel.apply {
            homePhotos.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    homeAdapter.submitData(it)
                }
            }
            photoClicked.observe(viewLifecycleOwner) {
                if (it!=null) {
                    val action =
                        it.let { HomeFragmentDirections.actionNavigationHomeToDetailOfImage(it) }
                    navController.navigate(action)
                }

            }
        }
        homeAdapter.apply {
            addLoadStateListener {
                swiperefresh.isRefreshing = it.refresh is LoadState.Loading
                home_recycler_view.isVisible=it.refresh is LoadState.NotLoading

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)

    }


}