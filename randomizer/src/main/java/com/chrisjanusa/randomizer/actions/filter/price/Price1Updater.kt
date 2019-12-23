package com.chrisjanusa.randomizer.actions.filter.price

import com.chrisjanusa.base_randomizer.RandomizerState

class Price1Updater(val selected: Boolean) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(price1TempSelected = selected)
    }
}