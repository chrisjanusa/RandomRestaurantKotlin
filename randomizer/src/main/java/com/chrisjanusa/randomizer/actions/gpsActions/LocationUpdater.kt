package com.chrisjanusa.randomizer.actions.gpsActions

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class LocationUpdater(private val location : String) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(locationText = location)
    }
}