package com.chrisjanusa.randomizer.filter_price.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Price

class TempPriceAddUpdater(private val price: Price) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = HashSet(prevState.priceTempSet)
        newSet.add(price)
        return prevState.copy(priceTempSet = newSet)
    }
}