package com.chrisjanusa.randomizer.filter_price.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Price

class TempPriceRemoveUpdater(private val price: Price) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = prevState.priceTempSet
        newSet.remove(price)
        return prevState.copy(priceTempSet = newSet)
    }
}