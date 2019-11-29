package com.chrisjanusa.randomizer.actions.filter.price

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class Price4Updater(val selected: Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(price4TempSelected = selected)
    }
}