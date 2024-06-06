package com.chrisjanusa.foursquare.models.business_search

data class Geocodes(
    val main: Coordinates,
    val front_door: Coordinates?
)