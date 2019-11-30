package com.chrisjanusa.randomizer.actions.filter.distance

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class SelectedDistanceUpdater(private val newMaxMiles : Float) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(maxMilesSelected = newMaxMiles)
    }
}