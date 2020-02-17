package com.chrisjanusa.randomizer.filter_price.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_price.PriceHelper

class SelectedPriceUpdater(private val priceSet: HashSet<PriceHelper.Price>) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(priceSet = HashSet(priceSet), restaurantCacheValid = false)
    }
}