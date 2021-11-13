package com.chrisjanusa.randomizer.yelp.actions

import android.util.Log
import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.yelp.*
import com.chrisjanusa.randomizer.yelp.events.InvalidLocationErrorEvent
import com.chrisjanusa.randomizer.yelp.events.ReviewRequestEvent
import com.chrisjanusa.randomizer.yelp.updaters.EmptyRestaurantsSeenRecentlyUpdater
import com.chrisjanusa.randomizer.yelp.updaters.IncreaseTimesRandomizedUpdater
import com.chrisjanusa.randomizer.yelp.updaters.ReviewRequestedUpdater
import com.chrisjanusa.yelp.models.Restaurant
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
            val restaurants = if (state.restaurants.isEmpty() || !state.restaurantCacheValid) {
                if (state.currLat == null || state.currLng == null) {
                    eventChannel.send(InvalidLocationErrorEvent())
                    return
                }

                notifyStartingToLoadRestaurants(state, updateChannel, eventChannel)

                startQueryingYelp(state, updateChannel, eventChannel, channel)

                var restaurants = try {
                        channel.receive().filter { !isRestaurantFiltered(state, it) }
                    } catch (exception: ClosedReceiveChannelException) {
                        return
                    }
                if (restaurants.isEmpty()) {
                    throwNoRestaurantError(state, updateChannel, eventChannel)
                    return
                }

                val filteredBLockedRestaurants = restaurants.filter { !isBlocked(state, it) }
                if (filteredBLockedRestaurants.isEmpty()) {
                    throwAllRestaurantsBlockedError(state, updateChannel, eventChannel)
                    return
                }
                val filteredRestaurants = filteredBLockedRestaurants.filter { !isRecentlySeen(state, it) }
                restaurants = checkIfAllRestaurantsAreFiltered(updateChannel, filteredRestaurants, filteredBLockedRestaurants)

                notifyFinishedLoadingRestaurants(restaurants, updateChannel, eventChannel)
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