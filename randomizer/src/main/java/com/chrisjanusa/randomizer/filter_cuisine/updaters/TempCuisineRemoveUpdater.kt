package com.chrisjanusa.randomizer.filter_cuisine.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Cuisine

class TempCuisineRemoveUpdater(private val cuisine: Cuisine) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = prevState.cuisineTempSet
        newSet.remove(cuisine)
        return prevState.copy(cuisineTempSet = newSet)
    }
}