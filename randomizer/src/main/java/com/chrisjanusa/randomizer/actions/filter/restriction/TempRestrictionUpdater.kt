package com.chrisjanusa.randomizer.actions.filter.restriction

import com.chrisjanusa.randomizer.helpers.RestrictionHelper.Restriction
import com.chrisjanusa.base_randomizer.RandomizerState

class TempRestrictionUpdater(private val selected: Restriction) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(restrictionTempSelected = selected)
    }
}