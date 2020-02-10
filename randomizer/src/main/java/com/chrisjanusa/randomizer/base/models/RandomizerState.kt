package com.chrisjanusa.randomizer.base.models

import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.Cuisine
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.defaultCuisineTitle
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistance
import com.chrisjanusa.randomizer.filter_price.PriceHelper.Price
import com.chrisjanusa.randomizer.filter_price.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.filter_diet.DietHelper.Diet
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

    val openNowSelected: Boolean = true,

    val favoriteOnlySelected: Boolean = false,

    val maxMilesSelected: Float = defaultDistance,
    val tempMaxMiles: Float = defaultDistance,

    val dietTempSelected: Diet = Diet.None,
    val diet: Diet = Diet.None,

    val cuisineSet: HashSet<Cuisine> = HashSet(),
    val cuisineString: String = defaultCuisineTitle,
    val cuisineTempSet: HashSet<Cuisine> = HashSet()
)