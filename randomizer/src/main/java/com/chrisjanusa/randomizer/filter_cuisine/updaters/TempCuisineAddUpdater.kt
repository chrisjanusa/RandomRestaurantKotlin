package com.chrisjanusa.randomizer.filter_cuisine.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Cuisine

class TempCuisineAddUpdater(private val cuisine: Cuisine) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = HashSet(prevState.cuisineTempSet)
        newSet.add(cuisine)
        return prevState.copy(cuisineTempSet = newSet)
    }
}