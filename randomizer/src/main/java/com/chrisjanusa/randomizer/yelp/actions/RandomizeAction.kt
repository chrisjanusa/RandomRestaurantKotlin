package com.chrisjanusa.randomizer.yelp.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.yelp.YelpHelper.queryYelp
import com.chrisjanusa.randomizer.yelp.YelpHelper.randomRestaurant
import com.chrisjanusa.randomizer.yelp.events.FinishedLoadingNewRestaurantsEvent
import com.chrisjanusa.randomizer.yelp.events.InvalidLocationErrorEvent
import com.chrisjanusa.randomizer.yelp.events.NoRestaurantsErrorEvent
import com.chrisjanusa.randomizer.yelp.events.StartLoadingNewRestaurantsEvent
import com.chrisjanusa.randomizer.yelp.updaters.*
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.*
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
                state.lastCacheUpdateJob?.cancel()
                updateChannel.send(CurrRestaurantUpdater(null))
                updateChannel.send(ClearRestaurantCacheUpdater())
                updateChannel.send(RestaurantCacheValidityUpdater(true))
                eventChannel.send(StartLoadingNewRestaurantsEvent())
                val job = GlobalScope.launch {  queryYelp(state, channel) }
                updateChannel.send(CacheJobUpdater(job))
                val restaurants = channel.receive()
                if (restaurants.isEmpty()) {
                    eventChannel.send(NoRestaurantsErrorEvent())
                    updateChannel.send(CurrRestaurantUpdater(state.currRestaurant))
                    eventChannel.send(FinishedLoadingNewRestaurantsEvent())
                    return
                }
                updateChannel.send(AddRestaurantsUpdater(restaurants))
                eventChannel.send(FinishedLoadingNewRestaurantsEvent())
                restaurants
            } else {
                channel.close()
                state.restaurants
            }
            val newRestaurant = randomRestaurant(restaurants)
            updateChannel.send(CurrRestaurantUpdater(newRestaurant))
            updateChannel.send(RemoveRestaurantUpdater(newRestaurant))
            newRestaurant.coordinates.run {
                mapChannel.send(MapUpdate(latitude, longitude, true))
            }
            for (restaurantList in channel) {
                updateChannel.send(AddRestaurantsUpdater(restaurantList))
            }

        }
    }
}