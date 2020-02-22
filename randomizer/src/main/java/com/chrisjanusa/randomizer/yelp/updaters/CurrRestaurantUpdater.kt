package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant

class CurrRestaurantUpdater(private val restaurant: Restaurant?) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(currRestaurant = restaurant)
    }

}