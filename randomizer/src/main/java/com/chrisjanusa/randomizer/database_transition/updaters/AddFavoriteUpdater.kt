package com.chrisjanusa.randomizer.database_transition.updaters

import android.content.Context
import android.content.SharedPreferences
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.preferences.PreferenceHelper.StateObject
import com.chrisjanusa.randomizer.database_transition.database.FavoritesDBHelper
import com.chrisjanusa.restaurantstorage.RestaurantPreferenceHelper.saveCache
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddFavoriteUpdater(
    private val restaurant: Restaurant,
    private val preferences: SharedPreferences?,
    private val context: Context
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = HashSet(prevState.favSet)
        newSet.add(restaurant)
        GlobalScope.launch {
            if (saveCache(preferences, StateObject.FavRestaurants.key, newSet)) {
                FavoritesDBHelper(context).delete(restaurant.name)
            }
        }
        return prevState.copy(favSet = newSet)
    }
}