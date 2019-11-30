package com.chrisjanusa.randomizer.actions.init

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class InitDistanceFilterUpdater(private val maxMiles: Float) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(tempMaxMiles = maxMiles)
    }
}