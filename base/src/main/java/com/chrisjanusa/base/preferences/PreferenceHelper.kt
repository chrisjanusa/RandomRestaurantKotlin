package com.chrisjanusa.base.preferences

import android.content.SharedPreferences
import com.chrisjanusa.base.models.*
import com.chrisjanusa.base.models.enums.Cuisine
import com.chrisjanusa.base.models.enums.Diet
import com.chrisjanusa.base.models.enums.Price
import com.chrisjanusa.restaurantstorage.retrieveListCache
import com.chrisjanusa.restaurantstorage.saveListCache

object PreferenceHelper {
    sealed class StateObject(val key: String) {
        object GpsOn : StateObject("gpsOn")
        object PriceSelected : StateObject("priceSelected")
        object OpenNowSelected : StateObject("openNowSelected")
        object FavoriteOnlySelected : StateObject("favoriteOnlySelected")
        object FastFoodSelected : StateObject("fastFoodSelected")
        object SitDownSelected : StateObject("sitDownSelected")
        object MaxMilesSelected : StateObject("maxMilesSelected")
        object Diet : StateObject("diet")
        object Cuisine : StateObject("cuisine")
        object Rating : StateObject("rating")
        object Latitude : StateObject("currLat")
        object Longitude : StateObject("currLng")
        object RestaurantsSeen : StateObject("restaurantsSeen")
        object CacheValidity : StateObject("cacheValidity")
        object FavRestaurants : StateObject("favorites")
        object BlockedRestaurants : StateObject("blocks")
        object History : StateObject("history")
        object TimesRandomized : StateObject("timesRandomized")
        object ReviewRequested : StateObject("reviewRequested")
    }

    fun saveState(state: RandomizerState, preferences: SharedPreferences?) {
        preferences?.let {
            with(it.edit()) {
                putInt(StateObject.TimesRandomized.key, state.timesRandomized)
                putBoolean(StateObject.ReviewRequested.key, state.reviewRequested)
                putBoolean(StateObject.GpsOn.key, state.gpsOn)
                putBoolean(StateObject.OpenNowSelected.key, state.openNowSelected)
                putBoolean(StateObject.FavoriteOnlySelected.key, state.favoriteOnlySelected)
                putBoolean(StateObject.FastFoodSelected.key, state.fastFoodSelected)
                putBoolean(StateObject.SitDownSelected.key, state.sitDownSelected)
                putFloat(StateObject.MaxMilesSelected.key, state.maxMilesSelected)
                putString(StateObject.Diet.key, state.diet.identifier)
                putString(StateObject.PriceSelected.key, state.priceSet.toSaveString())
                putString(StateObject.Cuisine.key, state.cuisineSet.toIdentifierString())
                putFloat(StateObject.Rating.key, state.minRating)
                state.currLat?.let { lat -> putString(StateObject.Latitude.key, "$lat") }
                state.currLng?.let { lng -> putString(StateObject.Longitude.key, "$lng") }
                putBoolean(StateObject.CacheValidity.key, state.restaurantCacheValid)
                putStringSet(StateObject.RestaurantsSeen.key, state.restaurantsSeenRecently)
                saveListCache(preferences, StateObject.BlockedRestaurants.key, state.blockList)
                saveListCache(preferences, StateObject.FavRestaurants.key, state.favList)
                saveListCache(preferences, StateObject.History.key, state.historyList)
                apply()
            }
        }
    }

    fun retrieveState(preferences: SharedPreferences?): PreferenceData? {
        return preferences?.run {
            PreferenceData(
                getInt(StateObject.TimesRandomized.key, 0),
                getBoolean(StateObject.ReviewRequested.key, false),
                getBoolean(StateObject.GpsOn.key, true),
                getBoolean(StateObject.OpenNowSelected.key, true),
                getBoolean(StateObject.FavoriteOnlySelected.key, false),
                getBoolean(StateObject.FastFoodSelected.key, false),
                getBoolean(StateObject.SitDownSelected.key, false),
                getFloat(StateObject.MaxMilesSelected.key, defaultDistance),
                getString(StateObject.Diet.key, Diet.None.identifier)
                    ?: Diet.None.identifier,
                getFloat(StateObject.Rating.key, 0f),
                getString(StateObject.PriceSelected.key, defaultPriceTitle) ?: defaultPriceTitle,
                getString(StateObject.Cuisine.key, defaultCuisineTitle) ?: "",
                getString(StateObject.Latitude.key, null)?.toDouble(),
                getString(StateObject.Longitude.key, null)?.toDouble(),
                getBoolean(StateObject.CacheValidity.key, false),
                getStringSet(StateObject.RestaurantsSeen.key, HashSet()) ?: HashSet(),
                retrieveListCache(preferences, StateObject.FavRestaurants.key),
                retrieveListCache(preferences, StateObject.BlockedRestaurants.key),
                retrieveListCache(preferences, StateObject.History.key)
            )
        }
    }

    private fun HashSet<Cuisine>.toIdentifierString(): String {
        if (this.isEmpty()) {
            return defaultCuisineTitle
        }
        val out = StringBuilder()
        for (cuisine in this.iterator()) {
            out.append(cuisine.identifier)
            out.append(delimiter)
        }
        return out.dropLast(2).toString()
    }

    private fun HashSet<Price>.toSaveString(): String {
        if (isEmpty()) {
            return defaultPriceTitle
        }
        val builder = StringBuilder()
        val priceList = listOf(Price.One, Price.Two, Price.Three, Price.Four)
        priceList.forEach { if (contains(it)) builder.append(it.text + delimiter) }
        return builder.dropLast(2).toString()
    }
}