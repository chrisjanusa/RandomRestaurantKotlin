package com.chrisjanusa.randomizer.actions.init

import com.chrisjanusa.base_randomizer.RandomizerState

class InitDistanceFilterUpdater(private val maxMiles: Float) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(tempMaxMiles = maxMiles)
    }
}