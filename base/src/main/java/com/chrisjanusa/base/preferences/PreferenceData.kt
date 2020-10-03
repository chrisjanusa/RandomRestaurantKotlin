package com.chrisjanusa.base.preferences

import com.chrisjanusa.yelp.models.Restaurant

data class PreferenceData(
    val gpsOn: Boolean,
    val openNowSelected: Boolean,
    val favoriteOnlySelected: Boolean,
    val fastFoodSelected: Boolean,
    val sitDownSelected: Boolean,
    val maxMilesSelected: Float,
    val diet: String,
    val rating: Float,
    val priceSelected: String,
    val cuisineString: String,
    val currLat: Double?,
    val currLng: Double?,
    val cacheValidity: Boolean,
    val restaurantsSeenRecently: Set<String>,
    val favList: List<Restaurant>,
    val blockList: List<Restaurant>,
    val history: List<Restaurant>
)