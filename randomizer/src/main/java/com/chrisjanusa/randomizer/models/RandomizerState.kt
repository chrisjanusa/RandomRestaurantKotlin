package com.chrisjanusa.randomizer.models

import android.location.Location
import com.chrisjanusa.randomizer.helpers.FilterHelper.Filter

data class RandomizerState (
    val gpsOn: Boolean = true,
    val locationText: String = "Test",
    val location: Location? = null,

    val filterOpen: Filter = Filter.None,

    val priceText: String = "",
    val price1TempSelected: Boolean = false,
    val price2TempSelected: Boolean = false,
    val price3TempSelected: Boolean = false,
    val price4TempSelected: Boolean = false,

    val openNowSelected: Boolean = false,

    val favoriteOnlySelected: Boolean = false
)