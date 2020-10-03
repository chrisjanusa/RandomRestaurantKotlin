package com.chrisjanusa.randomizer.database_transition.updaters

import android.content.Context
import android.content.SharedPreferences
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.preferences.PreferenceHelper.StateObject
import com.chrisjanusa.randomizer.database_transition.database.FavoritesDBHelper
import com.chrisjanusa.restaurantstorage.RestaurantPreferenceHelper.saveListCache
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AddFavoriteUpdater(
    private val restaurant: Restaurant,
    private val preferences: SharedPreferences?,
    private val context: Context
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newList = LinkedList(prevState.favList)
        newList.add(restaurant)
        GlobalScope.launch {
            if (saveListCache(preferences, StateObject.FavRestaurants.key, newList)) {
                FavoritesDBHelper(context).delete(restaurant.name)
            }
        }
        return prevState.copy(favList = newList)
    }
}