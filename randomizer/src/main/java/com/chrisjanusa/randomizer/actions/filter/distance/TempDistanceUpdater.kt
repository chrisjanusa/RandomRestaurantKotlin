package com.chrisjanusa.randomizer.actions.filter.distance

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class TempDistanceUpdater(private val newDist: Float) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(tempMaxMiles = newDist)
    }
}