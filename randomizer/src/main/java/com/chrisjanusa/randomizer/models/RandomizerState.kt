package com.chrisjanusa.randomizer.models

import android.location.Location
import com.chrisjanusa.randomizer.helpers.CategoryHelper
import com.chrisjanusa.randomizer.helpers.DistanceHelper.defaultDistance
import com.chrisjanusa.randomizer.helpers.FilterHelper.Filter
import com.chrisjanusa.randomizer.helpers.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.helpers.RestrictionHelper.Restriction

data class RandomizerState (
    val gpsOn: Boolean = true,
    val locationText: String = "Test",
    val location: Location? = null,

    val filterOpen: Filter = Filter.None,

    val priceText: String = defaultPriceTitle,
    val price1TempSelected: Boolean = false,
    val price2TempSelected: Boolean = false,
    val price3TempSelected: Boolean = false,
    val price4TempSelected: Boolean = false,

    val openNowSelected: Boolean = false,

    val favoriteOnlySelected: Boolean = false,

    val maxMilesSelected: Float = defaultDistance,
    val tempMaxMiles: Float = defaultDistance,

    val restrictionTempSelected: Restriction = Restriction.None,
    val restriction: Restriction = Restriction.None,

    val categorySet: HashSet<CategoryHelper.Category> = HashSet(),
    val categoryString: String = "",
    val categoryTempSet: HashSet<CategoryHelper.Category> = HashSet(),

    val addressSearchString: String = ""
)