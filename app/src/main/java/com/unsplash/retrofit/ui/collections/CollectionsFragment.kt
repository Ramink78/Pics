package com.unsplash.retrofit.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unsplash.retrofit.*
import com.unsplash.retrofit.adapters.CollectionsAdapter
import com.unsplash.retrofit.adapters.CollectionsAdapter2
import com.unsplash.retrofit.adapters.OnLoadMoreListener
import com.unsplash.retrofit.adapters.OnPhotoClickListener
import com.unsplash.retrofit.data.API_KEY
import com.unsplash.retrofit.data.collections.CollectionsAPI
import com.unsplash.retrofit.data.details.model.Photo
import com.unsplash.retrofit.repo.collections.CollectionRepo
import com.unsplash.retrofit.ui.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CollectionsFragment : Fragment() {

    private lateinit var collectionsViewModel: CollectionsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutm: LinearLayoutManager
    private lateinit var navController: NavController
    lateinit var collectionRepo:CollectionRepo


    private var collectionsAdapter: CollectionsAdapter2?=null
    var page = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectionRepo= CollectionRepo(ServiceBuilder.buildService(CollectionsAPI::class.java))
        collectionsViewModel =getViewModel()

        return inflater.inflate(R.layout.fragment_collections, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
       recyclerView = view.findViewById(R.id.collections_recyceler)
        layoutm = LinearLayoutManager(requireContext())
        collectionsAdapter= CollectionsAdapter2((requireContext()))
        recyclerView.apply {
            layoutManager = layoutm
            adapter = collectionsAdapter
        }
       // loadCollections()

     /*   collectionsAdapter?.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMoreData() {
                loadMore()
            }
        })*/
        collectionsViewModel.collections.observe(viewLifecycleOwner, Observer {
            collectionsAdapter?.submitList(it)


        })


        collectionsAdapter?.setOnPhotoClickListener(object : OnPhotoClickListener {

            override fun onClick(
                id: String?, position: Int,
                view: View,
                photo: Photo?
            ) {
                val action= id?.let {
                    CollectionsFragmentDirections.actionNavigationCollectionsToCollectionPhotosFragment(
                        it
                    )
                }
                if (action != null) {
                    navController.navigate(action)
                }

            }


        })
    }

    /*private fun loadCollections() {
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getCollections(API_KEY, 1, 50)
        call.enqueue(object : Callback<Collections> {
            override fun onResponse(call: Call<Collections>, response: Response<Collections>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        collectionsAdapter?.clear()
                        collectionsAdapter?.addItems(result)
                    }
                }
            }

            override fun onFailure(call: Call<Collections>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loadMore() {
        page++
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getCollections(API_KEY, page, 50)
        call.enqueue(object : Callback<Collections> {
            override fun onResponse(call: Call<Collections>, response: Response<Collections>) {
                if (response.isSuccessful) {
                    // Log.i("Mytag","ok")
                    val result = response.body()
                    if (result != null) {
                        collectionsAdapter?.addItems(result)
                    }
                }
            }

            override fun onFailure(call: Call<Collections>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }*/
    private fun getViewModel(): CollectionsViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("cast eception")
                return CollectionsViewModel(collectionRepo)  as T
            }
        })[CollectionsViewModel::class.java]
    }


}