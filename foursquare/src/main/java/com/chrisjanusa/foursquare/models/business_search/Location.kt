package com.chrisjanusa.foursquare.models.business_search

data class Location(
    val address: String? = null,
    // City
    val locality: String? = null,
    // State
    val region: String? = null,
    val formatted_address: String? = null
)