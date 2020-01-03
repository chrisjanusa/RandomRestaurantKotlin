package com.chrisjanusa.randomizer.filter_restriction.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.base.models.RandomizerState

class SelectedRestrictionUpdater(private val restriction: Restriction) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(restriction = restriction)
    }
}