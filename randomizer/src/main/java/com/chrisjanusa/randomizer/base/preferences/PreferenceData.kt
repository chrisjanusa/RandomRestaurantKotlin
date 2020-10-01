package com.chrisjanusa.randomizer.base.preferences

import com.chrisjanusa.yelp.models.Restaurant

data class PreferenceData(
    val gpsOn: Boolean,
    val openNowSelected: Boolean,
    val favoriteOnlySelected: Boolean,
    val fastFoodSelected: Boolean,
    val sitDownSelected: Boolean,
    val maxMilesSelected: Float,
    val diet: String,
    val priceSelected: String,
    val cuisineString: String,
    val currLat: Double?,
    val currLng: Double?,
    val cacheValidity: Boolean,
    val restaurantsSeenRecently: Set<String>,
    val favSet: Set<Restaurant>,
    val blockSet: Set<Restaurant>,
    val history: List<Restaurant>
)