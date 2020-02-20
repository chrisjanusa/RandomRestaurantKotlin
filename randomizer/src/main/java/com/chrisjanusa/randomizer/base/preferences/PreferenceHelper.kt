package com.chrisjanusa.randomizer.base.preferences

import android.content.SharedPreferences
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.defaultCuisineTitle
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.toIdentifierString
import com.chrisjanusa.randomizer.filter_price.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.filter_price.PriceHelper.toSaveString
import com.chrisjanusa.randomizer.filter_diet.DietHelper
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistance
import com.chrisjanusa.yelp.models.Restaurant
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object PreferenceHelper {
    sealed class StateObject(val key: String) {
        object GpsOn : StateObject("gpsOn")
        object PriceSelected : StateObject("priceSelected")
        object OpenNowSelected : StateObject("openNowSelected")
        object FavoriteOnlySelected : StateObject("favoriteOnlySelected")
        object MaxMilesSelected : StateObject("maxMilesSelected")
        object Diet : StateObject("diet")
        object Cuisine : StateObject("cuisine")
        object Latitude : StateObject("currLat")
        object Longitude : StateObject("currLng")
        object CurrRestaurant : StateObject("currRestaurant")
        object Cache : StateObject("cache")
        object CacheValidity : StateObject("cacheValidity")
    }

    fun saveState(state: RandomizerState, preferences: SharedPreferences?) {
        preferences?.let {
            with(it.edit()) {
                clear()
                putBoolean(StateObject.GpsOn.key, state.gpsOn)
                putBoolean(StateObject.OpenNowSelected.key, state.openNowSelected)
                putBoolean(StateObject.FavoriteOnlySelected.key, state.favoriteOnlySelected)
                putFloat(StateObject.MaxMilesSelected.key, state.maxMilesSelected)
                putString(StateObject.Diet.key, state.diet.identifier)
                putString(StateObject.PriceSelected.key, state.priceSet.toSaveString())
                putString(StateObject.Cuisine.key, state.cuisineSet.toIdentifierString())
                state.currLat?.let { lat -> putString(StateObject.Latitude.key, "$lat") }
                state.currLng?.let { lng -> putString(StateObject.Longitude.key, "$lng") }
                putString(StateObject.CurrRestaurant.key, jacksonObjectMapper().writeValueAsString(state.currRestaurant))
                putString(StateObject.Cache.key, jacksonObjectMapper().writeValueAsString(state.restaurants))
                putBoolean(StateObject.CacheValidity.key, state.restaurantCacheValid)
                apply()
            }
        }
    }

    fun retrieveState(preferences: SharedPreferences?): PreferenceData? {
        return preferences?.run {
            val currRestaurantString = getString(StateObject.CurrRestaurant.key, null)
            val restaurantListString = getString(StateObject.Cache.key, null)
            PreferenceData(
                getBoolean(StateObject.GpsOn.key, true),
                getBoolean(StateObject.OpenNowSelected.key, true),
                getBoolean(StateObject.FavoriteOnlySelected.key, false),
                getFloat(StateObject.MaxMilesSelected.key, defaultDistance),
                getString(StateObject.Diet.key, DietHelper.Diet.None.identifier)
                    ?: DietHelper.Diet.None.identifier,
                getString(StateObject.PriceSelected.key, defaultPriceTitle) ?: defaultPriceTitle,
                getString(StateObject.Cuisine.key, defaultCuisineTitle) ?: "",
                getString(StateObject.Latitude.key, null)?.toDouble(),
                getString(StateObject.Longitude.key, null)?.toDouble(),
                currRestaurantString?.let{ jacksonObjectMapper().readValue<Restaurant>(it) },
                restaurantListString?.let{ jacksonObjectMapper().readValue<List<Restaurant>>(it) } ?: ArrayList(),
                getBoolean(StateObject.CacheValidity.key, false)
            )
        }
    }
}