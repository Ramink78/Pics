package com.unsplash.retrofit.data.searchdata

data class Sponsorship(
    val impression_urls: List<String>,
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: Any
)