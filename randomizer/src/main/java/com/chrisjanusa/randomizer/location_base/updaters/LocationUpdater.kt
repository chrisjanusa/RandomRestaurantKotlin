package com.chrisjanusa.randomizer.location_base.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class LocationUpdater(private val locationStr: String, private val currLat: Double, private val currLng: Double) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(locationText = locationStr, currLat = currLat, currLng = currLng)
    }
}