package com.chrisjanusa.randomizer.base.preferences

import com.chrisjanusa.yelp.models.Restaurant

data class PreferenceData(
    val gpsOn: Boolean,
    val openNowSelected: Boolean,
    val favoriteOnlySelected: Boolean,
    val maxMilesSelected: Float,
    val diet: String,
    val priceSelected: String,
    val cuisineString: String,
    val currLat: Double?,
    val currLng: Double?,
    val currRestaurant: Restaurant?,
    val restaurants: List<Restaurant>,
    val cacheValidity: Boolean
)