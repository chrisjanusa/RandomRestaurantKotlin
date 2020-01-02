package com.chrisjanusa.randomizer.location_search.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class SearchStringUpdater(val addressSearchString: String) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(addressSearchString = addressSearchString)
    }
}