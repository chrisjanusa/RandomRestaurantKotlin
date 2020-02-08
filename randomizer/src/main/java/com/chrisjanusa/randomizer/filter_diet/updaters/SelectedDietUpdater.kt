package com.chrisjanusa.randomizer.filter_diet.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_diet.DietHelper.Diet

class SelectedDietUpdater(private val diet: Diet) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(diet = diet)
    }
}