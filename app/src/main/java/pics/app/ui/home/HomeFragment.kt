package pics.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.LoadState
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import pics.app.PicsApp
import pics.app.R
import pics.app.adapters.HomeAdapters
import pics.app.adapters.OnPhotoClickListener
import pics.app.data.dp
import pics.app.data.photo.PhotoAPI
import pics.app.data.photo.model.Photo
import pics.app.ui.recyclerview.ItemSpacing
import retrofit2.Retrofit
import javax.inject.Inject


class HomeFragment : Fragment() {
    private lateinit var homeadapter: HomeAdapters
    private lateinit var navController: NavController
    @Inject lateinit var service: PhotoAPI
    @Inject
    lateinit var retrofit: Retrofit
    @Inject
    lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        reenterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition2)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeadapter = HomeAdapters(requireContext())
        navController = Navigation.findNavController(view)
        home_recyceler.apply {
            addItemDecoration(ItemSpacing(24.dp(), 2))
            adapter = homeadapter

        }
        homeViewModel.homePhotos.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                homeadapter.submitData(it)
            }
        }

        homeadapter.addLoadStateListener {
            swiperefresh.isRefreshing = it.refresh is LoadState.Loading

        }
        homeadapter.setOnPhotoClickListener(object : OnPhotoClickListener {
            override fun onClick(
                id: String?, position: Int,
                view: View,
                photo: Photo?

            ) {
                val action =
                    photo.let { HomeFragmentDirections.actionNavigationHomeToDetailOfImage(it!!) }

                val extras = FragmentNavigatorExtras(view to (id ?: "no id"))
                navController.navigate(action, extras)

            }


        })

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)
    }


}