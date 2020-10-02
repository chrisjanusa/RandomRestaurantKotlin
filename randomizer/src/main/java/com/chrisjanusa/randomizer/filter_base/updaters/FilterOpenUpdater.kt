package com.chrisjanusa.randomizer.filter_base.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Filter

class FilterOpenUpdater(private val filterOpen: Filter?) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(filterOpen = filterOpen)
    }
}