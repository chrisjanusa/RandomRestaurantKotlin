package com.chrisjanusa.randomizer.filter_cuisine.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.Cuisine

class SelectedCuisineUpdater(private val cuisineSet: HashSet<Cuisine>) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(cuisineSet = cuisineSet, restaurantCacheValid = false, restaurantsSeenRecently = HashSet())
    }
}