package com.chrisjanusa.randomizer.filter_boolean.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import java.util.HashSet

class SitDownUpdater(private val sitDownSelected: Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(sitDownSelected = sitDownSelected, restaurantCacheValid = false, restaurantsSeenRecently = HashSet())
    }
}