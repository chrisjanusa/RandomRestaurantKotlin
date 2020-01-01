package com.chrisjanusa.randomizer.search.updaters

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class SearchStringUpdater(val addressSearchString: String) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(addressSearchString = addressSearchString)
    }
}