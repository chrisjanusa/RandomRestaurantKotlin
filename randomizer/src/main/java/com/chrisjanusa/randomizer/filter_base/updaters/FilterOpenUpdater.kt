package com.chrisjanusa.randomizer.filter_base.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter

class FilterOpenUpdater(private val filterOpen: Filter?) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(filterOpen = filterOpen)
    }
}