package com.chrisjanusa.randomizer.filter_base.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.filter_base.FilterHelper
import com.chrisjanusa.randomizer.base.models.RandomizerState

class FilterOpenUpdater(private val filterOpen: FilterHelper.Filter) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(filterOpen = filterOpen)
    }
}