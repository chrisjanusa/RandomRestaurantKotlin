package com.chrisjanusa.randomizer.base.models

import android.location.Location
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistance
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category
import com.chrisjanusa.randomizer.filter_price.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLocation
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLocationText

data class RandomizerState (
    val gpsOn: Boolean = true,
    val locationText: String = defaultLocationText,
    val location: Location = defaultLocation,

    val addressSearchString: String = "",
    val lastManualLocation: Location = defaultLocation,
    val lastManualLocationText: String = defaultLocationText,

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

    val categorySet: HashSet<Category> = HashSet(),
    val categoryString: String = "",
    val categoryTempSet: HashSet<Category> = HashSet()
)