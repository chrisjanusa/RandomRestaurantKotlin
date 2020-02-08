package com.chrisjanusa.randomizer.filter_diet.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_diet.DietHelper.Diet

class TempDietUpdater(private val selected: Diet) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(dietTempSelected = selected)
    }
}