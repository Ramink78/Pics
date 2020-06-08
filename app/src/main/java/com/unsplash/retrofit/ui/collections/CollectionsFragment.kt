package com.unsplash.retrofit.ui.collections

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unsplash.retrofit.*
import com.unsplash.retrofit.adapters.CollectionsAdapters
import com.unsplash.retrofit.data.collections.Collections
import com.unsplash.retrofit.data.collections.CollectionsData
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
    private lateinit var layoutm: GridLayoutManager
    private lateinit var arraylist: ArrayList<CollectionsData>
    var endOfList=false
    var isScroling=false
    var isloading = false
    var page=1
    val API_KEY="Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0"
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        collectionsViewModel =
            ViewModelProviders.of(this).get(CollectionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_collections, container, false)
        layoutm = GridLayoutManager(context, 2)

        if (container != null) {
            recyclerView = root.findViewById(R.id.collections_recyceler)
            recyclerView.apply {

                setHasFixedSize(true)
                layoutManager = layoutm
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val outOfScroll = layoutm.findFirstCompletelyVisibleItemPosition()
                        val onScreen = layoutm.childCount
                        val total = layoutm.itemCount
                        if (isScroling && (outOfScroll + onScreen == total)) {
                            Log.i("Scroll", "End of list")
                            loadMore()
                            isScroling = false

                        }


                    }

                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int
                    ) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            isScroling = true
                        }

                    }
                })

            }
            loadCollections()
        }

            collectionsViewModel.text.observe(viewLifecycleOwner, Observer {
            })
            return root
        }
        fun loadCollections() {
            val request = ServiceBuilder.buildService(API::class.java)
            val call = request.getCollections(API_KEY, 1, 20)
            call.enqueue(object : Callback<Collections> {
                override fun onResponse(call: Call<Collections>, response: Response<Collections>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result != null) {
                            arraylist = result
                            recyclerView.adapter = CollectionsAdapters(requireContext(), arraylist)
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
                            arraylist.addAll(result)
                            recyclerView.adapter!!.notifyDataSetChanged()

                        }
                    }
                }

                override fun onFailure(call: Call<Collections>, t: Throwable) {
                }
            })
        }

}