package com.chrisjanusa.randomizer.actions.filter.restriction

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.helpers.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.models.RandomizerState

class TempRestrictionUpdater(private val selected: Restriction) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(restrictionTempSelected = selected)
    }
}