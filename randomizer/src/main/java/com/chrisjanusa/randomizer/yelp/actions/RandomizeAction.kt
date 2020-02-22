package com.chrisjanusa.randomizer.yelp.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.yelp.YelpHelper.monitorBackgroundThreads
import com.chrisjanusa.randomizer.yelp.YelpHelper.notifyFinishedLoadingRestaurants
import com.chrisjanusa.randomizer.yelp.YelpHelper.notifyStartingToLoadRestaurants
import com.chrisjanusa.randomizer.yelp.YelpHelper.setRandomRestaurant
import com.chrisjanusa.randomizer.yelp.YelpHelper.startQueryingYelp
import com.chrisjanusa.randomizer.yelp.YelpHelper.throwNoRestaurantError
import com.chrisjanusa.randomizer.yelp.events.InvalidLocationErrorEvent
import com.chrisjanusa.randomizer.yelp.updaters.*
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.channels.Channel

class RandomizeAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.let { state ->
            val channel = Channel<List<Restaurant>>(Channel.UNLIMITED)
            val restaurants = if (state.restaurants.isEmpty() || !state.restaurantCacheValid) {
                if (state.currLat == null || state.currLng == null) {
                    eventChannel.send(InvalidLocationErrorEvent())
                    return
                }

                notifyStartingToLoadRestaurants(state, updateChannel, eventChannel)

                startQueryingYelp(state, updateChannel, channel)

                var restaurants = channel.receive()

                if (restaurants.isEmpty()) {
                    throwNoRestaurantError(state, updateChannel, eventChannel)
                    return
                }

                val filteredRestaurants = restaurants.filter { !state.restaurantsSeenRecently.contains(it.id) }
                restaurants = checkIfAllRestaurantsAreFiltered(updateChannel, filteredRestaurants, restaurants)

                notifyFinishedLoadingRestaurants(restaurants, updateChannel, eventChannel)
                restaurants
            } else {
                channel.close()
                state.restaurants
            }

            setRandomRestaurant(restaurants, updateChannel, mapChannel)

            monitorBackgroundThreads(channel, updateChannel)
        }
    }

    private suspend fun checkIfAllRestaurantsAreFiltered(
        updateChannel: Channel<BaseUpdater>,
        filteredRestaurants: List<Restaurant>,
        restaurants: List<Restaurant>
    ): List<Restaurant> {
        return if (filteredRestaurants.isEmpty()) {
            updateChannel.send(EmptyRestaurantsSeenRecentlyUpdater())
            restaurants
        } else {
            filteredRestaurants
        }
    }
}