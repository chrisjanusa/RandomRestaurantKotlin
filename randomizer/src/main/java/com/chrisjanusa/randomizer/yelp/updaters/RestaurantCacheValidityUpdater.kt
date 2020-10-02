package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import java.util.HashSet

class RestaurantCacheValidityUpdater(private val validity: Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val restaurantsSeenRecently = if (validity) prevState.restaurantsSeenRecently else HashSet()
        return prevState.copy(restaurantCacheValid = validity, restaurantsSeenRecently = restaurantsSeenRecently)
    }
}