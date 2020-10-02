package com.chrisjanusa.restaurant_base.restaurant_favorite.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant

class FavoriteUpdater(private val restaurant: Restaurant) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = HashSet(prevState.favSet)
        if (newSet.contains(restaurant)) {
            newSet.remove(restaurant)
        } else {
            newSet.add(restaurant)
        }
        return prevState.copy(favSet = newSet)
    }
}