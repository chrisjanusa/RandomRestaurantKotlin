package com.chrisjanusa.randomizer.yelp

import android.util.Log
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Diet
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.toYelpString
import com.chrisjanusa.randomizer.filter_distance.milesToMeters
import com.chrisjanusa.randomizer.filter_price.setToYelpString
import com.chrisjanusa.randomizer.filter_rating.isDefault
import com.chrisjanusa.randomizer.yelp.events.*
import com.chrisjanusa.randomizer.yelp.updaters.*
import com.chrisjanusa.yelp.YelpRepository
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


private const val restaurantsPerQuery = 50
private const val numberOfRestaurants = 500

private suspend fun queryYelp(
    state: RandomizerState,
    channel: Channel<List<Restaurant>>,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {

    state.run {
        val queryLat = currLat
        val queryLng = currLng
        if (queryLng == null || queryLat == null) {
            return
        }

        val categories = when {
            cuisineSet.isNotEmpty() -> cuisineSet.toYelpString()
            diet != Diet.None -> diet.identifier
            else -> null
        }

        var term = "restaurants"
        term = when {
            fastFoodSelected && !sitDownSelected -> "fast food $term"
            !fastFoodSelected && sitDownSelected -> "table service $term"
            else -> term
        }
        term =
            if (cuisineSet.isNotEmpty() && diet != Diet.None) "${diet.identifier} $term" else term

        val radius = milesToMeters(maxMilesSelected).roundToInt()
        val price = setToYelpString(priceSet)
        try {
            queryYelpAtOffset(
                latitude = queryLat,
                longitude = queryLng,
                term = term,
                radius = radius,
                categories = categories,
                offset = 0,
                price = price,
                limit = restaurantsPerQuery,
                open_now = openNowSelected,
                channel = channel
            )
        } catch (throwable: Throwable) {
            if (throwable !is CancellationException) {
                throwQueryError(state, updateChannel, eventChannel)
            }
            Log.d("Query Error", throwable.toString())
            channel.close()
            return
        }
        try {
            for (i in restaurantsPerQuery until numberOfRestaurants step restaurantsPerQuery) {
                queryYelpAtOffset(
                    latitude = queryLat,
                    longitude = queryLng,
                    term = term,
                    radius = radius,
                    categories = categories,
                    offset = i,
                    price = price,
                    limit = restaurantsPerQuery,
                    open_now = openNowSelected,
                    channel = channel
                )
            }
        } catch (throwable: Throwable) {
            Log.d("Query Error", throwable.toString())
        } finally {
            channel.close()
        }
    }
}

private suspend fun queryYelpAtOffset(
    latitude: Double,
    longitude: Double,
    term: String = "restaurants",
    radius: Int = 8047,
    categories: String? = null,
    limit: Int = 50,
    offset: Int = 0,
    price: String? = null,
    open_now: Boolean = true,
    channel: Channel<List<Restaurant>>
) {
    val restaurants = YelpRepository.getBusinessSearchResults(
        latitude = latitude,
        longitude = longitude,
        term = term,
        radius = radius,
        categories = categories,
        offset = offset,
        price = price,
        limit = limit,
        open_now = open_now
    ).businesses
    channel.send(restaurants)
}

private fun randomRestaurant(restaurants: List<Restaurant>): Restaurant {
    return restaurants.random()
}

suspend fun monitorBackgroundThreads(
    channel: Channel<List<Restaurant>>,
    updateChannel: Channel<BaseUpdater>
) {
    for (restaurants in channel) {
        updateChannel.send(AddRestaurantsUpdater(restaurants))
    }
}

suspend fun notifyStartingToLoadRestaurants(
    state: RandomizerState,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {
    state.lastCacheUpdateJob?.cancel()
    updateChannel.send(CurrRestaurantUpdater(null))
    updateChannel.send(ClearRestaurantCacheUpdater())
    updateChannel.send(RestaurantCacheValidityUpdater(true))
    eventChannel.send(StartLoadingNewRestaurantsEvent())
}

suspend fun notifyFinishedLoadingRestaurants(
    restaurants: List<Restaurant>,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {
    updateChannel.send(AddRestaurantsUpdater(restaurants))
    eventChannel.send(FinishedLoadingNewRestaurantsEvent())
}

suspend fun startQueryingYelp(
    state: RandomizerState,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>,
    channel: Channel<List<Restaurant>>
) {
    val job = GlobalScope.launch { queryYelp(state, channel, updateChannel, eventChannel) }
    updateChannel.send(CacheJobUpdater(job))
}

suspend fun throwNoRestaurantError(
    state: RandomizerState,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {
    eventChannel.send(NoRestaurantsErrorEvent())
    throwRestaurantError(state, updateChannel, eventChannel)
}

suspend fun throwAllRestaurantsBlockedError(
    state: RandomizerState,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {
    eventChannel.send(AllRestaurantsBlockedErrorEvent())
    throwRestaurantError(state, updateChannel, eventChannel)
}

suspend fun throwQueryError(
    state: RandomizerState,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {
    eventChannel.send(YelpQueryErrorEvent())
    throwRestaurantError(state, updateChannel, eventChannel)
}

private suspend fun throwRestaurantError(
    state: RandomizerState,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {
    updateChannel.send(CurrRestaurantUpdater(state.currRestaurant))
    eventChannel.send(LoadThumbnailEvent(state.currRestaurant?.image_url))
    eventChannel.send(FinishedLoadingNewRestaurantsEvent())
}

suspend fun setRandomRestaurant(
    restaurants: List<Restaurant>,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>,
    mapChannel: Channel<MapEvent>
) {
    val newRestaurant = randomRestaurant(restaurants)
    setRestaurant(newRestaurant, updateChannel, eventChannel, mapChannel)
    updateChannel.send(RemoveRestaurantUpdater(newRestaurant))

}

suspend fun setRestaurant(
    restaurant: Restaurant?,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>,
    mapChannel: Channel<MapEvent>
) {
    updateChannel.send(CurrRestaurantUpdater(restaurant))
    if (restaurant == null) {
        return
    }
    eventChannel.send(LoadThumbnailEvent(restaurant.image_url))
    restaurant.coordinates.run {
        mapChannel.send(MapEvent(latitude, longitude, true))
    }
    updateChannel.send(HistoryUpdater(restaurant))
}

fun isBlocked(prevState: RandomizerState, restaurant: Restaurant) =
    prevState.blockList.contains(restaurant)


fun isRecentlySeen(prevState: RandomizerState, restaurant: Restaurant) =
    prevState.restaurantsSeenRecently.contains(restaurant.id)

fun isTooFar(prevState: RandomizerState, restaurant: Restaurant) =
    milesToMeters(prevState.maxMilesSelected).roundToInt() < restaurant.distance

fun isTooLowRating(prevState: RandomizerState, restaurant: Restaurant): Boolean {
    val rating = restaurant.rating
    return if (rating == null) {
        !isDefault(prevState.minRating)
    } else {
        rating < prevState.minRating
    }
}

fun favoriteCheck(state: RandomizerState, restaurant: Restaurant): Boolean =
    !state.favoriteOnlySelected || state.favList.contains(restaurant)

fun isRestaurantFiltered(state: RandomizerState, restaurant: Restaurant) =
    isTooFar(state, restaurant)
            || !favoriteCheck(state, restaurant)
            || isTooLowRating(state, restaurant)


fun isValidRestaurant(state: RandomizerState, restaurant: Restaurant) =
    !(isRecentlySeen(state, restaurant)
            || isBlocked(state, restaurant)
            || isRestaurantFiltered(state, restaurant)
            )
