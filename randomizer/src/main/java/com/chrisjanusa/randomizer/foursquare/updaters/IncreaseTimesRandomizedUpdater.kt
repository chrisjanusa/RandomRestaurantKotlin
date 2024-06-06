package com.chrisjanusa.randomizer.foursquare.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState

class IncreaseTimesRandomizedUpdater : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(timesRandomized = prevState.timesRandomized+1)
    }
}