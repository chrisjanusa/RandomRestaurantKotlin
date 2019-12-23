package com.chrisjanusa.randomizer.actions.filter.price

import com.chrisjanusa.base_randomizer.RandomizerState

class SelectedPriceUpdater(private val priceText: String) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(priceText = priceText)
    }
}