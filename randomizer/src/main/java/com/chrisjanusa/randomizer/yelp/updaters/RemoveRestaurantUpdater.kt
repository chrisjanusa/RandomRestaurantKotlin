package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant
import java.util.*

class RemoveRestaurantUpdater(private val restaurant: Restaurant) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newRestaurantList = LinkedList(prevState.restaurants)
        newRestaurantList.remove(restaurant)
        val newRestaurantsSeenRecently = HashSet(prevState.restaurantsSeenRecently)
        newRestaurantsSeenRecently.add(restaurant.id)
        return prevState.copy(restaurants = newRestaurantList, restaurantsSeenRecently = newRestaurantsSeenRecently)
    }
}