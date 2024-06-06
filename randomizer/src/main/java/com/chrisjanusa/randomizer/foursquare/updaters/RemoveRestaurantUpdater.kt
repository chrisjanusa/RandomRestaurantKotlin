package com.chrisjanusa.randomizer.foursquare.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.restaurant.Restaurant
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