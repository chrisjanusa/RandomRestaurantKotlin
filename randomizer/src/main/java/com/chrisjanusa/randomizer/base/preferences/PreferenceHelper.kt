package com.chrisjanusa.randomizer.base.preferences

import android.content.SharedPreferences
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.defaultCuisineTitle
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.toIdentifierString
import com.chrisjanusa.randomizer.filter_price.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.filter_price.PriceHelper.toSaveString
import com.chrisjanusa.randomizer.filter_diet.DietHelper
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistance
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLat
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLng

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
    }

    fun saveState(state: RandomizerState, preferences: SharedPreferences?) {
        preferences?.let {
            with(it.edit()) {
                putBoolean(StateObject.GpsOn.key, state.gpsOn)
                putBoolean(StateObject.OpenNowSelected.key, state.openNowSelected)
                putBoolean(StateObject.FavoriteOnlySelected.key, state.favoriteOnlySelected)
                putFloat(StateObject.MaxMilesSelected.key, state.maxMilesSelected)
                putString(StateObject.Diet.key, state.diet.identifier)
                putString(StateObject.PriceSelected.key, state.priceSet.toSaveString())
                putString(StateObject.Cuisine.key, state.cuisineSet.toIdentifierString())
                state.currLat?.let { lat -> putString(StateObject.Latitude.key, "$lat") }
                state.currLng?.let { lng -> putString(StateObject.Longitude.key, "$lng") }
                apply()
            }
        }
    }

    fun retrieveState(preferences: SharedPreferences?): PreferenceData? {
        return preferences?.run {
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
                getString(StateObject.Longitude.key, null)?.toDouble()
            )
        }
    }
}