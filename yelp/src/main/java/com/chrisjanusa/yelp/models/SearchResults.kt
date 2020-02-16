package com.chrisjanusa.yelp.models

data class SearchResults(
    val businesses: List<Restaurant>,
    val region: Region
)