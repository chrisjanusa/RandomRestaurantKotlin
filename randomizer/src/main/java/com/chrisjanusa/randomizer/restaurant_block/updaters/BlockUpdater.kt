package com.chrisjanusa.randomizer.restaurant_block.updaters

import android.content.SharedPreferences
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant

class BlockUpdater(private val restaurant: Restaurant, private val preferences: SharedPreferences?) : BaseUpdater {
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