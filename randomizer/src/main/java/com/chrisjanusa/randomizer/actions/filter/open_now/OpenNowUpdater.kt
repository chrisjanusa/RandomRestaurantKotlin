package com.chrisjanusa.randomizer.actions.filter.open_now

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class OpenNowUpdater(val openNow : Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(openNowSelected = openNow)
    }
}