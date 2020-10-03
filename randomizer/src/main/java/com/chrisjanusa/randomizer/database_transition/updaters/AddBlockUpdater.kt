package com.chrisjanusa.randomizer.database_transition.updaters

import android.content.Context
import android.content.SharedPreferences
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.preferences.PreferenceHelper.StateObject.BlockedRestaurants
import com.chrisjanusa.randomizer.database_transition.database.FavoritesDBHelper
import com.chrisjanusa.restaurantstorage.RestaurantPreferenceHelper.saveListCache
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AddBlockUpdater(
    private val restaurant: Restaurant,
    private val preferences: SharedPreferences?,
    private val context: Context
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newList = LinkedList(prevState.blockList)
        newList.add(restaurant)
        GlobalScope.launch {
            if (saveListCache(preferences, BlockedRestaurants.key, newList)) {
                FavoritesDBHelper(context).delete(restaurant.name)
            }
        }
        return prevState.copy(blockList = newList)
    }
}