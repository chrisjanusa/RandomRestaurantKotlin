package com.chrisjanusa.randomizer.filter_price.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_price.PriceHelper

class TempPriceAddUpdater(private val price: PriceHelper.Price) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = prevState.priceTempSet
        newSet.add(price)
        return prevState.copy(priceTempSet = newSet)
    }
}