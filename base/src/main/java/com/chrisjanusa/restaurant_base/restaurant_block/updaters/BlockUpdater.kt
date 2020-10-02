package com.chrisjanusa.restaurant_base.restaurant_block.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant

class BlockUpdater(private val restaurant: Restaurant) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = HashSet(prevState.blockSet)
        if (newSet.contains(restaurant)) {
            newSet.remove(restaurant)
        } else {
            newSet.add(restaurant)
        }
        return prevState.copy(blockSet = newSet)
    }
}