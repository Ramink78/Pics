package com.unsplash.retrofit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.unsplash.retrofit.R
import com.unsplash.retrofit.adapters.HomeAdapters
import com.unsplash.retrofit.adapters.OnLoadMoreListener
import com.unsplash.retrofit.adapters.OnPhotoClickListener
import com.unsplash.retrofit.data.details.Photo
import com.unsplash.retrofit.ui.recyclerview.ItemSpacing
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeadapter: HomeAdapters
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeadapter = HomeAdapters(requireContext())
        navController = Navigation.findNavController(view)

        home_swiperefresh.setOnRefreshListener {
         //   homeViewModel.loadPhotos()
        }
        recyclerView = view.findViewById(R.id.home_recyceler)
        recyclerView.apply {
            addItemDecoration(ItemSpacing(resources.getDimensionPixelSize(R.dimen.item_space), 2))
            adapter = homeadapter
        }


        homeViewModel.photos.observe(viewLifecycleOwner, Observer {
            homeadapter.addItems(it)
            home_swiperefresh.isRefreshing = false
        })
        homeViewModel.loadPhotos()
        homeadapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMoreData() {
                //homeViewModel.loadMore()
            }
        })
        homeadapter.setOnPhotoClickListener(object : OnPhotoClickListener {
            override fun onClick(
                id: String?, position: Int,
                view: View,
                photo: Photo

            ) {
                Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
              val action =
                    photo.let { HomeFragmentDirections.actionNavigationHomeToDetailOfImage(it) }
                val extras = FragmentNavigatorExtras(view to (id ?: "no id"))
                navController.navigate(action, extras)

            }


        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Toast.makeText(requireContext(), "on saved", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(requireContext(), "on pused", Toast.LENGTH_SHORT).show()
    }





}