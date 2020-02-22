package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant
import java.util.*

class AddRestaurantsUpdater(private val newRestaurants : List<Restaurant>) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newRestaurantList = LinkedList(prevState.restaurants)
        newRestaurantList.addAll(newRestaurants.filter { !prevState.restaurantsSeenRecently.contains(it.id) })
        return prevState.copy(restaurants = newRestaurantList)
    }
}