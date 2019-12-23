package com.chrisjanusa.randomizer.actions.filter.restriction

import com.chrisjanusa.randomizer.helpers.RestrictionHelper.Restriction
import com.chrisjanusa.base_randomizer.RandomizerState

class SelectedRestrictionUpdater(private val restriction: Restriction) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(restriction = restriction)
    }
}