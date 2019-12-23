package com.chrisjanusa.randomizer.actions.filter.open_now

import com.chrisjanusa.base_randomizer.RandomizerState

class OpenNowUpdater(val openNow : Boolean) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(openNowSelected = openNow)
    }
}