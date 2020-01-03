package com.chrisjanusa.randomizer.base.preferences

data class PreferenceData(
    val gpsOn: Boolean,
    val openNowSelected: Boolean,
    val favoriteOnlySelected: Boolean,
    val maxMilesSelected: Float,
    val restriction: String,
    val priceSelected: String,
    val categoryString: String,
    val currLat: Double,
    val currLng: Double
)