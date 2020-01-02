package com.chrisjanusa.randomizer.base.models

import android.location.Location
import com.chrisjanusa.randomizer.filter_category.CategoryHelper
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistance
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_price.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.defaultLocation
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.defaultLocationText

data class RandomizerState (
    val gpsOn: Boolean = true,
    val locationText: String = defaultLocationText,
    val location: Location = defaultLocation,

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