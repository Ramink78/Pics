package com.unsplash.retrofit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.Explode
import androidx.transition.TransitionInflater
import com.unsplash.retrofit.R
import com.unsplash.retrofit.ServiceBuilder
import com.unsplash.retrofit.adapters.HomeAdapters
import com.unsplash.retrofit.adapters.OnPhotoClickListener
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.data.photo.PhotoAPI
import com.unsplash.retrofit.network.NetworkState
import com.unsplash.retrofit.repo.home.HomePhotosRepo
import com.unsplash.retrofit.ui.recyclerview.ItemSpacing
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


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
        enterTransition=TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
        exitTransition=TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
        reenterTransition =  TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition2)
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
            homeadapter.networkState=it
            when(it){
                NetworkState.INITIALIZING->progressBar.visibility=View.VISIBLE
                else->progressBar.visibility=View.GONE
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
                return HomeViewModel(homePhotosRepo)  as T
            }
        })[HomeViewModel::class.java]
    }


}