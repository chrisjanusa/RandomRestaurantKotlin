package com.chrisjanusa.randomizer.location_shared.updaters

import android.location.Location
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class LocationUpdater(private val locationStr : String, val location: Location) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(locationText = locationStr, location = location)
    }
}