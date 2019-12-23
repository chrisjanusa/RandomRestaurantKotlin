package com.chrisjanusa.randomizer.actions.filter.distance

import com.chrisjanusa.base_randomizer.RandomizerState

class TempDistanceUpdater(private val newDist: Float) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(tempMaxMiles = newDist)
    }
}