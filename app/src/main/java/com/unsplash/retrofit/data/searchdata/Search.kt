package com.unsplash.retrofit.data.searchdata

data class Search(
    val results: ArrayList<Result>,
    val total: Int,
    val total_pages: Int
)