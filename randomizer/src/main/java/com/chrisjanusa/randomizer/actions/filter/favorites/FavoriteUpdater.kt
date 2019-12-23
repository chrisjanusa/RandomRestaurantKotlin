package com.chrisjanusa.randomizer.actions.filter.favorites

import com.chrisjanusa.base_randomizer.RandomizerState

class FavoriteUpdater(val favoriteSelected : Boolean) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(favoriteOnlySelected = favoriteSelected)
    }
}