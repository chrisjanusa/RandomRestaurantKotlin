package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class RestaurantCacheValidityUpdater(private val validity: Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(restaurantCacheValid = validity)
    }
}