package com.chrisjanusa.randomizer.actions.init

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class InitUpdater(val gpsOn: Boolean) :BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(gpsOn = gpsOn)
    }
}