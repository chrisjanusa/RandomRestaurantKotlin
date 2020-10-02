package com.chrisjanusa.randomizer.restaurant_favorite.updaters

import android.content.SharedPreferences
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant

class FavoriteUpdater(private val restaurant: Restaurant, private val preferences: SharedPreferences?) : BaseUpdater {
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