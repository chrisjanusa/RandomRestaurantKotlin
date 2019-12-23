package com.chrisjanusa.randomizer.actions.gpsActions

import android.location.Location
import com.chrisjanusa.base_randomizer.RandomizerState

class LocationUpdater(private val locationStr : String, val location: Location) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(locationText = locationStr, location = location)
    }
}