package com.chrisjanusa.randomizer.filter_diet.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Diet

class TempDietUpdater(private val selected: Diet) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(dietTempSelected = selected)
    }
}