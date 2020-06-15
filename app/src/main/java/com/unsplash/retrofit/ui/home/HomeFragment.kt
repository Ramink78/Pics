package com.unsplash.retrofit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.unsplash.retrofit.*
import com.unsplash.retrofit.adapters.HomeAdapters
import com.unsplash.retrofit.adapters.OnLoadMoreListener
import com.unsplash.retrofit.adapters.OnPhotoClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var layoutm: StaggeredGridLayoutManager

    private val homeadapter = HomeAdapters()
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
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutm = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        recyclerView = view.findViewById(R.id.home_recyceler)
        recyclerView.apply {
            layoutManager = layoutm
        }

        recyclerView.adapter = homeadapter

        loadNames()

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        homeadapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMoreData() {
                loadMore()
            }
        })
        homeadapter.setOnPhotoClickListener(object :OnPhotoClickListener{
            override fun onClick(position: Int) {
                Toast.makeText(requireContext(),"Clicked On Position :$position",Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun loadNames() {
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getPhotos(API_KEY, 1, 25)
        call.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        homeadapter.clear()
                        homeadapter.addItems(result)

                    }
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(requireContext(),"Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loadMore() {
        page++
        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getPhotos(API_KEY, page, 25)
        call.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    // Log.i("Mytag","ok")
                    val result = response.body()
                    if (result != null) {
                        homeadapter.addItems(result)
                    }
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(requireContext(),"Connection Error $t",Toast.LENGTH_SHORT).show()
            }
        })
    }

}