package com.chrisjanusa.randomizer.actions.filter.favorites

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class FavoriteUpdater(val favoriteSelected : Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(favoriteOnlySelected = favoriteSelected)
    }
}