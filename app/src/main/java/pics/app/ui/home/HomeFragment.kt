package pics.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_home.*
import pics.app.R
import pics.app.ServiceBuilder
import pics.app.adapters.HomeAdapters
import pics.app.adapters.OnPhotoClickListener
import pics.app.data.details.model.Photo
import pics.app.data.photo.PhotoAPI
import pics.app.network.NetworkState
import pics.app.repo.home.HomePhotosRepo
import pics.app.ui.recyclerview.ItemSpacing


class HomeFragment : Fragment() {
    private lateinit var homeadapter: HomeAdapters
    private lateinit var navController: NavController
    lateinit var homePhotosRepo: HomePhotosRepo
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        homePhotosRepo = HomePhotosRepo(ServiceBuilder.buildService(PhotoAPI::class.java))
        homeViewModel = getViewModel()
        reenterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition2)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeadapter = HomeAdapters(requireContext())
        navController = Navigation.findNavController(view)
        home_recyceler.apply {
            addItemDecoration(ItemSpacing(resources.getDimensionPixelSize(R.dimen.item_space), 2))
            adapter = homeadapter

        }

        homeViewModel.homephotos.observe(viewLifecycleOwner, Observer {
            homeadapter.submitList(it)

        })
        homeViewModel.networkstate.observe(viewLifecycleOwner, Observer {
            homeadapter.networkState = it
            when (it) {
                NetworkState.INITIALIZING -> progressBar.visibility = View.VISIBLE
                else -> progressBar.visibility = View.GONE
            }

        })
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


    private fun getViewModel(): HomeViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("cast eception")
                return HomeViewModel(homePhotosRepo) as T
            }
        })[HomeViewModel::class.java]
    }


}