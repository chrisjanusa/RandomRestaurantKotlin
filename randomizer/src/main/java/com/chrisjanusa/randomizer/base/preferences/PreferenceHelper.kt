package com.chrisjanusa.randomizer.base.preferences

import android.content.SharedPreferences
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper
import com.chrisjanusa.randomizer.filter_price.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.defaultLat
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.defaultLng

object PreferenceHelper {
    sealed class StateObject(val key: String) {
        object GpsOn : StateObject("gpsOn")
        object PriceSelected : StateObject("priceSelected")
        object OpenNowSelected : StateObject("openNowSelected")
        object FavoriteOnlySelected : StateObject("favoriteOnlySelected")
        object MaxMilesSelected : StateObject("maxMilesSelected")
        object Restriction : StateObject("restriction")
        object Category : StateObject("category")
        object Latitude : StateObject("curr_lat")
        object Longitude : StateObject("curr_lng")
    }

    fun saveState(state: RandomizerState, preferences: SharedPreferences?) {
        preferences?.let {
            with(it.edit()) {
                putBoolean(PreferenceHelper.StateObject.GpsOn.key, state.gpsOn)
                putBoolean(PreferenceHelper.StateObject.OpenNowSelected.key, state.openNowSelected)
                putBoolean(PreferenceHelper.StateObject.FavoriteOnlySelected.key, state.favoriteOnlySelected)
                putFloat(PreferenceHelper.StateObject.MaxMilesSelected.key, state.maxMilesSelected)
                putString(PreferenceHelper.StateObject.Restriction.key, state.restriction.identifier)
                putString(PreferenceHelper.StateObject.PriceSelected.key, state.priceText)
                putString(PreferenceHelper.StateObject.Category.key, state.categoryString)
                state.location?.run {
                    putFloat(PreferenceHelper.StateObject.Latitude.key, latitude.toFloat())
                    putFloat(PreferenceHelper.StateObject.Longitude.key, longitude.toFloat())
                }
                apply()
            }
        }
    }

    fun retrieveState(preferences: SharedPreferences?): PreferenceData? {
        return preferences?.run {
            PreferenceData(
                getBoolean(PreferenceHelper.StateObject.GpsOn.key, true),
                getBoolean(PreferenceHelper.StateObject.OpenNowSelected.key, false),
                getBoolean(PreferenceHelper.StateObject.FavoriteOnlySelected.key, false),
                getFloat(PreferenceHelper.StateObject.MaxMilesSelected.key, DistanceHelper.defaultDistance),
                getString(PreferenceHelper.StateObject.Restriction.key, RestrictionHelper.Restriction.None.identifier)
                    ?: RestrictionHelper.Restriction.None.identifier,
                getString(PreferenceHelper.StateObject.PriceSelected.key, defaultPriceTitle) ?: defaultPriceTitle,
                getString(PreferenceHelper.StateObject.Category.key, "") ?: "",
                getFloat(PreferenceHelper.StateObject.Latitude.key, defaultLat.toFloat()).toDouble(),
                getFloat(PreferenceHelper.StateObject.Longitude.key, defaultLng.toFloat()).toDouble()
            )
        }
    }
}