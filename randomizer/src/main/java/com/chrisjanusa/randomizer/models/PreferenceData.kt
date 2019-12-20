package com.chrisjanusa.randomizer.models

data class PreferenceData(
    val gpsOn: Boolean,
    val openNowSelected: Boolean,
    val favoriteOnlySelected : Boolean,
    val maxMilesSelected : Float,
    val restriction: String,
    val priceSelected: String
)