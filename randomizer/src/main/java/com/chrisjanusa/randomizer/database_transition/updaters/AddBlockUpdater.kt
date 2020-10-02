package com.chrisjanusa.randomizer.database_transition.updaters

import android.content.Context
import android.content.SharedPreferences
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.preferences.PreferenceHelper.StateObject.BlockedRestaurants
import com.chrisjanusa.randomizer.database_transition.database.FavoritesDBHelper
import com.chrisjanusa.restaurantstorage.RestaurantPreferenceHelper.saveCache
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBlockUpdater(
    private val restaurant: Restaurant,
    private val preferences: SharedPreferences?,
    private val context: Context
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = HashSet(prevState.blockSet)
        newSet.add(restaurant)
        GlobalScope.launch {
            if(saveCache(preferences, BlockedRestaurants.key, newSet)) {
                FavoritesDBHelper(context).delete(restaurant.name)
            }
        }
        return prevState.copy(blockSet = newSet)
    }
}