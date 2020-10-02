package com.chrisjanusa.randomizer.filter_price.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Price

class SelectedPriceUpdater(private val priceSet: HashSet<Price>) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(priceSet = HashSet(priceSet), restaurantCacheValid = false, restaurantsSeenRecently = HashSet())
    }
}