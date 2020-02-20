package com.chrisjanusa.randomizer.base.preferences

data class PreferenceData(
    val gpsOn: Boolean,
    val openNowSelected: Boolean,
    val favoriteOnlySelected: Boolean,
    val maxMilesSelected: Float,
    val diet: String,
    val priceSelected: String,
    val cuisineString: String,
    val currLat: Double?,
    val currLng: Double?
)