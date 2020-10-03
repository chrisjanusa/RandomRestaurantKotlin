package com.chrisjanusa.restaurant_base.restaurant_favorite.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant
import java.util.*

class FavoriteUpdater(private val restaurant: Restaurant) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newList = LinkedList(prevState.favList)
        if (newList.contains(restaurant)) {
            newList.remove(restaurant)
        } else {
            newList.addFirst(restaurant)
        }
        return prevState.copy(favList = newList)
    }
}