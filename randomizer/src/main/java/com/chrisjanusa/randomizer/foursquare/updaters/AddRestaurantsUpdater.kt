package com.chrisjanusa.randomizer.foursquare.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.foursquare.isValidRestaurant
import com.chrisjanusa.restaurant.Restaurant
import java.util.*

class AddRestaurantsUpdater(private val newRestaurants : List<Restaurant>) : BaseUpdater {//, private val strictCheck: Boolean = true) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newRestaurantList = LinkedList(prevState.restaurants)
        newRestaurantList.addAll(newRestaurants.filter {  isValidRestaurant(prevState, it)})//, strictCheck)})
        return prevState.copy(restaurants = newRestaurantList)
    }
}