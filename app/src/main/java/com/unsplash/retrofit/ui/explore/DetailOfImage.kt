package com.unsplash.retrofit.ui.explore

import android.icu.text.MessagePattern
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unsplash.retrofit.API
import com.unsplash.retrofit.R
import com.unsplash.retrofit.ServiceBuilder
import com.unsplash.retrofit.adapters.DetailsAdapter
import com.unsplash.retrofit.data.details.Detail
import com.unsplash.retrofit.data.details.Details
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailOfImage : Fragment() {
    lateinit var recyclerView: RecyclerView
    var lmanager: LinearLayoutManager? = null
    val API_KEY = "Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_of_image, container, false)
    }

    val args: DetailOfImageArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.detailsRV)
        lmanager = LinearLayoutManager(requireContext())
        recyclerView.apply {
            layoutManager = lmanager
        }
        getDetails(args.id)

    }


    private fun getDetails(id: String) {

        val request = ServiceBuilder.buildService(API::class.java)
        val call = request.getDetails(id, API_KEY)
        call.enqueue(object : Callback<Detail> {
            override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    val detailsAdapter = DetailsAdapter(result!!)
                    recyclerView.adapter = detailsAdapter

                }
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection Error $t", Toast.LENGTH_SHORT).show()
            }
        })

    }
}