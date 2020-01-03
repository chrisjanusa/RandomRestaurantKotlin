package com.chrisjanusa.randomizer.filter_distance.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class SelectedDistanceUpdater(private val newMaxMiles: Float) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(maxMilesSelected = newMaxMiles)
    }
}