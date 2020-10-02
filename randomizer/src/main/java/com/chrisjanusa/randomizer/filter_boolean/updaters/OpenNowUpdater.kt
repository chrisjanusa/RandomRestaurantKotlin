package com.chrisjanusa.randomizer.filter_boolean.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import java.util.HashSet

class OpenNowUpdater(private val openNow: Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(openNowSelected = openNow, restaurantCacheValid = false, restaurantsSeenRecently = HashSet())
    }
}