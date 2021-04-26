package com.chrisjanusa.randomizer.init

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Cuisine
import com.chrisjanusa.base.models.enums.Diet
import com.chrisjanusa.base.models.enums.Price
import com.chrisjanusa.yelp.models.Restaurant

class InitUpdater(
    private val timesRandomized: Int,
    private val reviewRequested: Boolean,
    private val gpsOn: Boolean,
    private val openNowSelected: Boolean,
    private val favoriteOnlySelected: Boolean,
    private val fastFoodSelected: Boolean,
    private val sitDownSelected: Boolean,
    private val maxMilesSelected: Float,
    private val diet: Diet,
    private val rating: Float,
    private val priceSet: HashSet<Price>,
    private val cuisineSet: HashSet<Cuisine>,
    private val currLat: Double?,
    private val currLng: Double?,
    private val locationText: String,
    private val cacheValidity: Boolean,
    private val restaurantsSeenRecently: Set<String>,
    private val favList: List<Restaurant>,
    private val blockList: List<Restaurant>,
    private val history: List<Restaurant>
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(
            timesRandomized = timesRandomized,
            reviewRequested = reviewRequested,
            gpsOn = gpsOn,
            priceSet = priceSet,
            openNowSelected = openNowSelected,
            favoriteOnlySelected = favoriteOnlySelected,
            fastFoodSelected = fastFoodSelected,
            sitDownSelected = sitDownSelected,
            maxMilesSelected = maxMilesSelected,
            diet = diet,
            minRating = rating,
            cuisineSet = cuisineSet,
            currLat = currLat,
            currLng = currLng,
            locationText = locationText,
            restaurantCacheValid = cacheValidity,
            stateInitialized = true,
            restaurantsSeenRecently = restaurantsSeenRecently,
            favList = favList,
            blockList = blockList,
            historyList = history
        )
    }
}