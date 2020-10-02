package com.chrisjanusa.randomizer.filter_diet.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Diet

class SelectedDietUpdater(private val diet: Diet) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(diet = diet, restaurantCacheValid = false, restaurantsSeenRecently = HashSet())
    }
}