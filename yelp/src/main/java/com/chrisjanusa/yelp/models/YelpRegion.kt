package com.chrisjanusa.yelp.models

object YelpRegion {
    data class Region(
        val center: Center
    )

    data class Center(
        val latitude: Double,
        val longitude: Double
    )
}

