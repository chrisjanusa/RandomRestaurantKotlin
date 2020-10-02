package com.chrisjanusa.randomizer.filter_cuisine.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Cuisine

class TempCuisineUpdater(private val cuisineTempSet: HashSet<Cuisine>) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(cuisineTempSet = cuisineTempSet)
    }
}