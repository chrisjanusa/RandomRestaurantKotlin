package com.chrisjanusa.randomizer.helpers

import android.content.SharedPreferences
import com.chrisjanusa.randomizer.helpers.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.models.PreferenceData
import com.chrisjanusa.randomizer.models.RandomizerState

object PreferenceHelper {
    sealed class StateObject(val key: String) {
        object GpsOn : StateObject("gpsOn")
        object PriceSelected : StateObject("priceSelected")
        object OpenNowSelected : StateObject("openNowSelected")
        object FavoriteOnlySelected : StateObject("favoriteOnlySelected")
        object MaxMilesSelected : StateObject("maxMilesSelected")
        object Restriction : StateObject("restriction")
    }

    fun saveState(state: RandomizerState, preferences: SharedPreferences?) {
        if (preferences != null) {
            with(preferences.edit()) {
                putBoolean(StateObject.GpsOn.key, state.gpsOn)
                putBoolean(StateObject.OpenNowSelected.key, state.openNowSelected)
                putBoolean(StateObject.FavoriteOnlySelected.key, state.favoriteOnlySelected)
                putFloat(StateObject.MaxMilesSelected.key, state.maxMilesSelected)
                putString(StateObject.Restriction.key, state.restriction.identifier)
                putString(StateObject.PriceSelected.key, state.priceText)
                apply()
            }
        }
    }

    fun retrieveState(preferences: SharedPreferences?): PreferenceData? {
        return preferences?.run {
            PreferenceData(
                getBoolean(StateObject.GpsOn.key, true),
                getBoolean(StateObject.OpenNowSelected.key, false),
                getBoolean(StateObject.FavoriteOnlySelected.key, false),
                getFloat(StateObject.MaxMilesSelected.key, DistanceHelper.defaultDistance),
                getString(StateObject.Restriction.key, RestrictionHelper.Restriction.None.identifier)
                    ?: RestrictionHelper.Restriction.None.identifier,
                getString(StateObject.PriceSelected.key, defaultPriceTitle) ?: defaultPriceTitle

            )
        }
    }
}