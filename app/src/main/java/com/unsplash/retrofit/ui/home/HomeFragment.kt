package com.unsplash.retrofit.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.unsplash.retrofit.*
import com.unsplash.retrofit.adapters.HomeAdapters
import com.unsplash.retrofit.adapters.endOfListListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment()  {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var layoutm: StaggeredGridLayoutManager
    private lateinit var arraylist: ArrayList<DataItem>
    private lateinit var homeadapter: HomeAdapters
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
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        layoutm = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        if (container != null) {
         recyclerView=   root.findViewById(R.id.home_recyceler)
             recyclerView.apply {
                 itemAnimator=DefaultItemAnimator()
                 layoutManager = layoutm

                 }
            loadNames()
            HomeAdapters.onEndListListener(object :endOfListListener{
                override fun onEndList() {
                    Log.i("Listener","End of list")
                    loadMore()
                }
            })
        }
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    fun loadNames(){
        val request= ServiceBuilder.buildService(API::class.java)
        val call=request.getRandom(API_KEY,1,20)
        call.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful){
                    val result=response.body()
                    if (result != null) {
                        arraylist=result
                        homeadapter=HomeAdapters(requireContext(), arraylist)
                        recyclerView.adapter=homeadapter

                    }
                }
            }
            override fun onFailure(call: Call<Data>, t: Throwable) {
            }
        })
    }
    fun loadMore(){
        page++
        val request= ServiceBuilder.buildService(API::class.java)
        val call=request.getRandom(API_KEY,page,20)
        call.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful){
                    // Log.i("Mytag","ok")
                    val result=response.body()
                    if (result != null) {
                    //    arraylist.addAll(result)
                       homeadapter.updateList(result)
                    //   recyclerView.adapter!!.notifyItemRangeInserted(arraylist.size-20,20)
                      // recyclerView.adapter!!.notifyDataSetChanged()

                    }
                }
            }
            override fun onFailure(call: Call<Data>, t: Throwable) {
            }
        })
    }

}