package com.chrisjanusa.randomizer.location_search.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState

class SearchStringUpdater(private val addressSearchString: String) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(addressSearchString = addressSearchString)
    }
}