package com.unsplash.retrofit.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.unsplash.retrofit.API
import com.unsplash.retrofit.R
import com.unsplash.retrofit.ServiceBuilder
import com.unsplash.retrofit.adapters.*
import com.unsplash.retrofit.data.API_KEY
import com.unsplash.retrofit.data.random.ExploreData
import com.unsplash.retrofit.data.searchdata.Search
import com.unsplash.retrofit.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_explore.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment : Fragment() {

    private lateinit var exploreViewModel: ExploreViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var layoutm: GridLayoutManager
    private lateinit var navController: NavController
    private lateinit var exploreAdapter : ExploreAdapter

    private val searchAdapter = SearchAdapter()
    private var page = 1
    private var childTag: String? = null
    private var onFilterChangeListener: OnFilterChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exploreViewModel =
            ViewModelProviders.of(this).get(ExploreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_explore, container, false)



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutm = GridLayoutManager(requireContext(), 3)
        exploreAdapter= ExploreAdapter(requireContext())
        chip_group.removeAllViews()
        navController = Navigation.findNavController(view)
        recyclerView = view.findViewById(R.id.explore_recyceler)
        recyclerView.apply {
            layoutManager = layoutm
        }

        recyclerView.adapter = exploreAdapter

        loadRandom()

        exploreViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        exploreAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMoreData() {
                loadMore()
            }
        })
        searchAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMoreData() {
                loadMoreFilter(childTag!!, searchAdapter)
            }

        })
        val filters = arrayListOf(
            "Animal",
            "Computer",
            "Color",
            "Children",
            "Dress",
            "House",
            "Phone",
            "Person",
            "Sky",
            "Vehicle"
        )

        chip_group.removeAllViews()
        filters.forEachIndexed { index, s ->
            val chip = Chip(chip_group.context, null, R.attr.MyChipStyle)
            chip.text = s
            chip.id = index
            chip.tag = s
            chip_group.addView(chip)
        }
        chip_group.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                val chip = group.getChildAt(chip_group.checkedChipId)
                childTag = chip.tag.toString()
                filterReq(childTag!!, searchAdapter)
            } else {
                recyclerView.swapAdapter(exploreAdapter, false)
            }
        }
    }

    private fun loadRandom() {
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getRandom(API_KEY, 30)
        call.enqueue(object : Callback<ExploreData> {
            override fun onResponse(call: Call<ExploreData>, response: Response<ExploreData>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        exploreAdapter.clear()
                        exploreAdapter.addItems(result)
                    }
                }
            }

            override fun onFailure(call: Call<ExploreData>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
        exploreAdapter.setOnPhotoClickListener(object : OnPhotoClickListener {
            override fun onClick(id: String?, position: Int,view: View) {
                val action = ExploreFragmentDirections.actionNavigationExploreToDetailOfImage(id!!)
                navController.navigate(action)
            }


        })
    }

    fun loadMore() {
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getRandom(API_KEY, 30)
        call.enqueue(object : Callback<ExploreData> {
            override fun onResponse(call: Call<ExploreData>, response: Response<ExploreData>) {
                if (response.isSuccessful) {
                    // Log.i("Mytag","ok")
                    val result = response.body()
                    if (result != null) {
                        exploreAdapter.addItems(result)

                    }
                }
            }

            override fun onFailure(call: Call<ExploreData>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterReq(keyword: String, adapter: SearchAdapter) {
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.photosFilter(API_KEY, page, 30, keyword)
        call.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        adapter.clear()
                        adapter.addItems(result.results)
                        recyclerView.adapter = searchAdapter


                    }
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadMoreFilter(keyword: String, adapter: SearchAdapter) {
        page++
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.photosFilter(API_KEY, page, 30, keyword)
        call.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        adapter.addItems(result.results)
                    }
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setOnFilterChangeListener(listener: OnFilterChangeListener) {
        onFilterChangeListener = listener
    }

}