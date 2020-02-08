package com.chrisjanusa.randomizer.filter_cuisine.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.Cuisine

class TempCuisineAddUpdater(private val cuisine: Cuisine) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = HashSet(prevState.cuisineTempSet)
        newSet.add(cuisine)
        return prevState.copy(cuisineTempSet = newSet)
    }
}