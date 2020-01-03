package com.chrisjanusa.randomizer.location_base.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class GpsStatusUpdater(private val gpsOn: Boolean) :
    BaseUpdater {

    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(gpsOn = gpsOn)
    }
}