package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.yelp.YelpHelper.isValidRestaurant
import com.chrisjanusa.yelp.models.Restaurant
import java.util.*

class AddRestaurantsUpdater(private val newRestaurants : List<Restaurant>) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newRestaurantList = LinkedList(prevState.restaurants)
        newRestaurantList.addAll(newRestaurants.filter {  isValidRestaurant(prevState, it)})
        return prevState.copy(restaurants = newRestaurantList)
    }
}