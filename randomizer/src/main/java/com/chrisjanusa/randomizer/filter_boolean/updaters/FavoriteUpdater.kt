package com.chrisjanusa.randomizer.filter_boolean.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class FavoriteUpdater(val favoriteSelected : Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(favoriteOnlySelected = favoriteSelected)
    }
}