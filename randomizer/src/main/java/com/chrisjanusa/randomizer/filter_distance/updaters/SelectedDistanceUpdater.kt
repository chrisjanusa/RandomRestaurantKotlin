package com.chrisjanusa.randomizer.filter_distance.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState

class SelectedDistanceUpdater(private val newMaxMiles: Float) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(maxMilesSelected = newMaxMiles, restaurantCacheValid = false, restaurantsSeenRecently = HashSet())
    }
}