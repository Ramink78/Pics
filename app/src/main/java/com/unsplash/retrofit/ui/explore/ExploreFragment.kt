package com.unsplash.retrofit.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.unsplash.retrofit.*
import com.unsplash.retrofit.adapters.ExploreAdapter
import com.unsplash.retrofit.adapters.OnLoadMoreListener
import com.unsplash.retrofit.adapters.OnPhotoClickListener
import com.unsplash.retrofit.data.random.Explore
import com.unsplash.retrofit.data.random.ExploreData
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
    private val exploreAdapter = ExploreAdapter()
    private val filters= arrayListOf("Animal","Computer","Color","Children","Dress","House","Phone","Person","Sky","Vehicle")
    val API_KEY = "Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0"
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
        for (element in filters){
            val chip=Chip(chip_group.context,null,R.attr.MyChipStyle)
            chip.text=element
            chip_group.addView(chip)

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
            override fun onClick(position: Int) {
                Toast.makeText(requireContext(),"Clicked On Position :$position",Toast.LENGTH_SHORT).show()
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

}