package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.yelp.models.Restaurant
import java.util.*

class HistoryUpdater(private val restaurant : Restaurant) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        var newRestaurantList = LinkedList(prevState.historyList)
        newRestaurantList.addFirst(restaurant)
        newRestaurantList = LinkedList(newRestaurantList.distinct().toMutableList())
        if (newRestaurantList.size > 100) {
            newRestaurantList.removeLast()
        }
        return prevState.copy(historyList = newRestaurantList)
    }
}