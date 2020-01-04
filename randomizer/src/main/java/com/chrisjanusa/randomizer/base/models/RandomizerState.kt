package com.chrisjanusa.randomizer.base.models

import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.defaultCategoryTitle
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistance
import com.chrisjanusa.randomizer.filter_price.PriceHelper.Price
import com.chrisjanusa.randomizer.filter_price.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLat
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLng
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLocationText

data class RandomizerState(
    val gpsOn: Boolean = true,
    val locationText: String = defaultLocationText,
    val currLat: Double = defaultLat,
    val currLng: Double = defaultLng,

    val addressSearchString: String = "",
    val lastManualLocationText: String = defaultLocationText,

    val filterOpen: Filter? = null,

    val priceText: String = defaultPriceTitle,
    val priceTempSet: HashSet<Price> = HashSet(),

    val openNowSelected: Boolean = false,

    val favoriteOnlySelected: Boolean = false,

    val maxMilesSelected: Float = defaultDistance,
    val tempMaxMiles: Float = defaultDistance,

    val restrictionTempSelected: Restriction = Restriction.None,
    val restriction: Restriction = Restriction.None,

    val categorySet: HashSet<Category> = HashSet(),
    val categoryString: String = defaultCategoryTitle,
    val categoryTempSet: HashSet<Category> = HashSet()
)