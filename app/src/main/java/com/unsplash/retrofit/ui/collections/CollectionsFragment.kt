package com.unsplash.retrofit.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.unsplash.retrofit.*
import com.unsplash.retrofit.adapters.CollectionsAdapter
import com.unsplash.retrofit.adapters.OnLoadMoreListener
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
    private val homeAdapter = CollectionsAdapter()

    var endOfList = false
    var isScroling = false
    var isloading = false
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
        layoutm = StaggeredGridLayoutManager( 2, RecyclerView.VERTICAL)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = layoutm
            adapter = homeAdapter
        }
        loadCollections()

        homeAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMoreData() {
                loadMore()
            }
        })

        collectionsViewModel.text.observe(viewLifecycleOwner, Observer {
        })
    }

    fun loadCollections() {
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getCollections(API_KEY, 1, 20)
        call.enqueue(object : Callback<Collections> {
            override fun onResponse(call: Call<Collections>, response: Response<Collections>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {

                        homeAdapter.addItems(result)
                    }
                }
            }

            override fun onFailure(call: Call<Collections>, t: Throwable) {
            }
        })
    }

    fun loadMore() {
        page++
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getCollections(API_KEY, page, 30)
        call.enqueue(object : Callback<Collections> {
            override fun onResponse(call: Call<Collections>, response: Response<Collections>) {
                if (response.isSuccessful) {
                    // Log.i("Mytag","ok")
                    val result = response.body()
                    if (result != null) {
                        homeAdapter.addItems(result)
                    }
                }
            }

            override fun onFailure(call: Call<Collections>, t: Throwable) {
            }
        })
    }

}