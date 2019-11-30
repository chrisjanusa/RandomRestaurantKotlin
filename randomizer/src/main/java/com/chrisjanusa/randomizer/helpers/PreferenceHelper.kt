package com.chrisjanusa.randomizer.helpers

import android.content.SharedPreferences
import com.chrisjanusa.randomizer.helpers.PriceHelper.defaultPriceTitle
import com.chrisjanusa.randomizer.models.PreferenceData
import com.chrisjanusa.randomizer.models.RandomizerState

object PreferenceHelper {
    private const val gpsOn = "gpsOn"
    private const val priceSelected = "priceSelected"

    fun saveState(state: RandomizerState, preferences: SharedPreferences?) {
        if (preferences != null) {
            with(preferences.edit()) {
                putBoolean(gpsOn, state.gpsOn)
                putString(priceSelected, state.priceText)
                apply()
            }
        }
    }

    fun retrieveState(preferences: SharedPreferences?) : PreferenceData? {
        if (preferences == null) {
            return null
        }
        return PreferenceData(preferences.getBoolean(gpsOn, true),
            preferences.getString(priceSelected, defaultPriceTitle)!!)
    }
}