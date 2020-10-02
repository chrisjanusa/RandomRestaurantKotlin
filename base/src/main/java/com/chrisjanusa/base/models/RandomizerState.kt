package com.chrisjanusa.base.models

import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.base.models.enums.Cuisine
import com.chrisjanusa.base.models.enums.Diet
import com.chrisjanusa.base.models.enums.Price
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.Job
import java.util.*
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

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
    val restaurantsSeenRecently: Set<String> = HashSet(),

    val favSet: Set<Restaurant> = HashSet(),
    val blockSet: Set<Restaurant> = HashSet(),
    val historyList: List<Restaurant> = LinkedList()
)