package com.chrisjanusa.randomizer.base.preferences

data class PreferenceData(
    val gpsOn: Boolean,
    val openNowSelected: Boolean,
    val favoriteOnlySelected: Boolean,
    val maxMilesSelected: Float,
    val restriction: String,
    val priceSelected: String,
    val categoryString: String,
    val curr_lat: Double,
    val curr_lng: Double
)