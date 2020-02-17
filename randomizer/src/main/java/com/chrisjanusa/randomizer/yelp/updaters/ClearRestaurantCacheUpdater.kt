package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import java.util.*

class ClearRestaurantCacheUpdater : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(restaurants = LinkedList())
    }
}