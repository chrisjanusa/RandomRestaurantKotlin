package com.chrisjanusa.base_filters.fragment.filters

import com.chrisjanusa.randomizer.helpers.FilterHelper
import com.chrisjanusa.base_randomizer.RandomizerState

class FilterOpenUpdater(private val filterOpen: FilterHelper.Filter) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(filterOpen = filterOpen)
    }
}