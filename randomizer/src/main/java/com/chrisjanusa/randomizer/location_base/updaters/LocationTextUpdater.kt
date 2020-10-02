package com.chrisjanusa.randomizer.location_base.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState

class LocationTextUpdater(private val locationStr: String) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(locationText = locationStr)
    }
}