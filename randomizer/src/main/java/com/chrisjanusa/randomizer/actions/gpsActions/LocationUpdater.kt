package com.chrisjanusa.randomizer.actions.gpsActions

import android.location.Location
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class LocationUpdater(private val locationStr : String, val location: Location) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(locationText = locationStr, location = location)
    }
}