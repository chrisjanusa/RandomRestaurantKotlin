package com.chrisjanusa.randomizer.filter_boolean.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class OpenNowUpdater(val openNow: Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(openNowSelected = openNow, restaurantCacheValid = false)
    }
}