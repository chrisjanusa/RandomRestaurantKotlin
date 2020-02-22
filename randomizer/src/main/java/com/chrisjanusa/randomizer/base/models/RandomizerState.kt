package com.chrisjanusa.randomizer.base.models

import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.Cuisine
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistance
import com.chrisjanusa.randomizer.filter_price.PriceHelper.Price
import com.chrisjanusa.randomizer.filter_diet.DietHelper.Diet
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLocationText
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.Job
import java.util.*
import kotlin.collections.HashSet

data class RandomizerState(
    val stateInitialized: Boolean = false,

    val gpsOn: Boolean = true,
    val locationText: String = defaultLocationText,
    val currLat: Double? = null,
    val currLng: Double? = null,

    val addressSearchString: String = "",
    val lastManualLocationText: String = defaultLocationText,

    val filterOpen: Filter? = null,

    val priceSet: HashSet<Price> = HashSet(),
    val priceTempSet: HashSet<Price> = HashSet(),

    val openNowSelected: Boolean = true,
    val favoriteOnlySelected: Boolean = false,
    val fastFoodSelected: Boolean = false,
    val sitDownSelected: Boolean = false,

    val maxMilesSelected: Float = defaultDistance,
    val tempMaxMiles: Float = defaultDistance,

    val dietTempSelected: Diet = Diet.None,
    val diet: Diet = Diet.None,

    val cuisineSet: HashSet<Cuisine> = HashSet(),
    val cuisineTempSet: HashSet<Cuisine> = HashSet(),

    val currRestaurant: Restaurant? = null,
    val restaurants: List<Restaurant> = LinkedList(),
    val restaurantCacheValid: Boolean = true,
    val lastCacheUpdateJob: Job? = null,
    val restaurantsSeenRecently: Set<String> = HashSet()
)