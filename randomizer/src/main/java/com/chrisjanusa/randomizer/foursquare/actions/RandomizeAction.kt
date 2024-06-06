package com.chrisjanusa.randomizer.foursquare.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.foursquare.*
import com.chrisjanusa.randomizer.foursquare.events.InvalidLocationErrorEvent
import com.chrisjanusa.randomizer.foursquare.events.ReviewRequestEvent
import com.chrisjanusa.randomizer.foursquare.updaters.EmptyRestaurantsSeenRecentlyUpdater
import com.chrisjanusa.randomizer.foursquare.updaters.IncreaseTimesRandomizedUpdater
import com.chrisjanusa.randomizer.foursquare.updaters.ReviewRequestedUpdater
import com.chrisjanusa.restaurant.Restaurant
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException

class RandomizeAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        currentState.value?.let { state ->
            val channel = Channel<List<Restaurant>>(Channel.UNLIMITED)
//            var strictCheck = true
            val restaurants = if (state.restaurants.isEmpty() || !state.restaurantCacheValid) {
                if (state.currLat == null || state.currLng == null) {
                    eventChannel.send(InvalidLocationErrorEvent())
                    return
                }

                notifyStartingToLoadRestaurants(state, updateChannel, eventChannel)

                startQueryingFoursquare(state, updateChannel, eventChannel, channel)

                var restaurants = try {
                        channel.receive()
                    } catch (exception: ClosedReceiveChannelException) {
                        return
                    }
                var filteredRestaurants = restaurants.filter { !isRestaurantFiltered(state, it) }//, strictCheck) }
//                if (filteredRestaurants.size < 25) {
//                    strictCheck = false
//                    filteredRestaurants = restaurants.filter { !isRestaurantFiltered(state, it, strictCheck) }
//                    if (filteredRestaurants.isEmpty()) {
//                        throwNoRestaurantError(state, updateChannel, eventChannel)
//                        return
//                    }
//                }

                val filteredBLockedRestaurants = filteredRestaurants.filter { !isBlocked(state, it) }
                if (filteredBLockedRestaurants.isEmpty()) {
                    throwAllRestaurantsBlockedError(state, updateChannel, eventChannel)
                    return
                }
                val filteredSeenRestaurants = filteredBLockedRestaurants.filter { !isRecentlySeen(state, it) }
                restaurants = checkIfAllRestaurantsAreFiltered(updateChannel, filteredSeenRestaurants, filteredBLockedRestaurants)

                notifyFinishedLoadingRestaurants(restaurants, updateChannel, eventChannel)//, strictCheck)
                restaurants
            } else {
                channel.close()
                state.restaurants
            }

            setRandomRestaurant(restaurants, updateChannel, eventChannel, mapChannel)
            if (state.timesRandomized >= 25 && !state.reviewRequested) {
                updateChannel.send(ReviewRequestedUpdater())
                eventChannel.send(ReviewRequestEvent())
            }
            updateChannel.send(IncreaseTimesRandomizedUpdater())
            monitorBackgroundThreads(channel, updateChannel)//, strictCheck)
        }
    }

    private suspend fun checkIfAllRestaurantsAreFiltered(
        updateChannel: Channel<BaseUpdater>,
        filteredRestaurants: List<Restaurant>,
        restaurants: List<Restaurant>
    ): List<Restaurant> {
        return filteredRestaurants.ifEmpty {
            updateChannel.send(EmptyRestaurantsSeenRecentlyUpdater())
            restaurants
        }
    }
}