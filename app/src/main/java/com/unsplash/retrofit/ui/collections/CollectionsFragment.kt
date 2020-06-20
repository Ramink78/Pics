package com.unsplash.retrofit.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.unsplash.retrofit.*
import com.unsplash.retrofit.adapters.CollectionsAdapter
import com.unsplash.retrofit.adapters.OnLoadMoreListener
import com.unsplash.retrofit.adapters.OnPhotoClickListener
import com.unsplash.retrofit.data.collections.Collections
import com.unsplash.retrofit.ui.home.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectionsFragment : Fragment() {

    private lateinit var collectionsViewModel: CollectionsViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var layoutm: StaggeredGridLayoutManager
    private val collectionsAdapter = CollectionsAdapter()
    var page = 1
    val API_KEY = "Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectionsViewModel =
            ViewModelProviders.of(this).get(CollectionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_collections, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.collections_recyceler)
        layoutm = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = layoutm
            adapter = collectionsAdapter
        }
        loadCollections()

        collectionsAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMoreData() {
                loadMore()
            }
        })

        collectionsViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        collectionsAdapter.setOnPhotoClickListener(object : OnPhotoClickListener {

            override fun onClick(id: String?, position: Int) {
                Toast.makeText(
                    requireContext(),
                    "Clicked On Position :$position",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })
    }

    private fun loadCollections() {
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getCollections(API_KEY, 1, 25)
        call.enqueue(object : Callback<Collections> {
            override fun onResponse(call: Call<Collections>, response: Response<Collections>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        collectionsAdapter.clear()
                        collectionsAdapter.addItems(result)
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
        val call = request.getCollections(API_KEY, page, 25)
        call.enqueue(object : Callback<Collections> {
            override fun onResponse(call: Call<Collections>, response: Response<Collections>) {
                if (response.isSuccessful) {
                    // Log.i("Mytag","ok")
                    val result = response.body()
                    if (result != null) {
                        collectionsAdapter.addItems(result)
                    }
                }
            }

            override fun onFailure(call: Call<Collections>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

}