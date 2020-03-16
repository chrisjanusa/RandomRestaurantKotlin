package com.chrisjanusa.randomizer.restaurant_block.updaters

import android.content.SharedPreferences
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.preferences.PreferenceHelper
import com.chrisjanusa.restaurantstorage.RestaurantPreferenceHelper.saveCache
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BlockUpdater(private val restaurant: Restaurant, private val preferences: SharedPreferences?) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = HashSet(prevState.blockSet)
        if (newSet.contains(restaurant)) {
            newSet.remove(restaurant)
        } else {
            newSet.add(restaurant)
        }
        GlobalScope.launch {
            saveCache(preferences, PreferenceHelper.StateObject.BlockedRestaurants.key, newSet)
        }
        return prevState.copy(blockSet = newSet)
    }
}