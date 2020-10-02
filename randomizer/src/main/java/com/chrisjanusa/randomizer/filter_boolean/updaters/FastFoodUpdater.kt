package com.chrisjanusa.randomizer.filter_boolean.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import java.util.HashSet

class FastFoodUpdater(private val fastFoodSelected: Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(fastFoodSelected = fastFoodSelected, restaurantCacheValid = false, restaurantsSeenRecently = HashSet())
    }
}