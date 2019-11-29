package com.chrisjanusa.randomizer.actions.filter.price

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.helpers.FilterHelper.Price
import com.chrisjanusa.randomizer.models.RandomizerState

class SelectedPriceUpdater(private val priceText: String) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(priceText = priceText)
    }
}