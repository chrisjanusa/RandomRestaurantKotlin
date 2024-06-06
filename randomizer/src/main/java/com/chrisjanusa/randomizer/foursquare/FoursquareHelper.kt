package com.chrisjanusa.randomizer.foursquare

import android.util.Log
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Diet
import com.chrisjanusa.foursquare.FourSquareRepository
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.toFoursquareString
import com.chrisjanusa.randomizer.filter_distance.milesToMeters
import com.chrisjanusa.randomizer.filter_rating.isDefault
import com.chrisjanusa.randomizer.foursquare.events.*
import com.chrisjanusa.randomizer.foursquare.updaters.*
import com.chrisjanusa.restaurant.Restaurant
import com.chrisjanusa.restaurant.toDomainModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.roundToInt


private const val restaurantsPerQuery = 50
private const val numberOfRestaurants = 500

private suspend fun queryFoursquare(
    state: RandomizerState,
    restaurantChannel: Channel<List<Restaurant>>,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {
    val nextPageChannel = Channel<String>(Channel.UNLIMITED)
    state.run {
        val queryLat = currLat
        val queryLng = currLng
        if (queryLng == null || queryLat == null) {
            return
        }

        val categories = when {
            cuisineSet.isNotEmpty() -> cuisineSet.toFoursquareString()
            diet != Diet.None -> diet.foursquare
            fastFoodSelected && !sitDownSelected -> "13145"
            else -> "13065"
        }

        val term = null
//        term = when {
//            fastFoodSelected && !sitDownSelected -> "fast food $term"
//            !fastFoodSelected && sitDownSelected -> "table service $term"
//            else -> term
//        }
//        term =
//            if (cuisineSet.isNotEmpty() && diet != Diet.None) "${diet.identifier} $term" else term

        val radius = milesToMeters(maxMilesSelected).roundToInt()
        val minPrice = priceSet.minByOrNull { it.num }?.num
        val maxPrice = priceSet.maxByOrNull { it.num }?.num
        val openNow = openNowSelected.takeIf { it }
        try {
            queryFoursquare(
                latitude = queryLat,
                longitude = queryLng,
                term = term,
                radius = radius,
                categories = categories,
                minPrice = minPrice,
                maxPrice = maxPrice,
                limit = restaurantsPerQuery,
                openNow = openNow,
                restaurantChannel = restaurantChannel,
                nextPageChannel = nextPageChannel
            )
        } catch (throwable: Throwable) {
            if (throwable !is CancellationException) {
                throwQueryError(state, updateChannel, eventChannel)
            }
            Log.d("Query Error", throwable.toString())
            restaurantChannel.close()
            nextPageChannel.close()
            return
        }
        try {
            for (i in restaurantsPerQuery until numberOfRestaurants step restaurantsPerQuery) {
                val cursor = nextPageChannel.receive()
                queryFoursquare(
                    latitude = queryLat,
                    longitude = queryLng,
                    term = term,
                    radius = radius,
                    categories = categories,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    limit = restaurantsPerQuery,
                    openNow = openNow,
                    cursor = cursor,
                    restaurantChannel = restaurantChannel,
                    nextPageChannel = nextPageChannel
                )
            }
        } catch (throwable: Throwable) {
            Log.d("Query Error", throwable.toString())
        } finally {
            restaurantChannel.close()
            nextPageChannel.close()
        }
    }
}

private suspend fun queryFoursquare(
    latitude: Double,
    longitude: Double,
    term: String? = null,
    radius: Int = 8047,
    categories: String? = null,
    limit: Int = 50,
    minPrice: Int? = null,
    maxPrice: Int? = null,
    openNow: Boolean? = null,
    cursor: String? = null,
    restaurantChannel: Channel<List<Restaurant>>,
    nextPageChannel: Channel<String>
) {
    val searchResults = FourSquareRepository.getBusinessSearchResults(
        latitude = latitude,
        longitude = longitude,
        term = term,
        radius = radius,
        categories = categories,
        minPrice = minPrice,
        maxPrice = maxPrice,
        limit = limit,
        openNow = openNow,
        cursor = cursor
    )
    val restaurants = searchResults.body()?.results?.map { it.toDomainModel() } ?: emptyList()
    restaurantChannel.send(restaurants)
    searchResults.headers().get("link")
        ?.replaceBefore("cursor=", "")
        ?.removePrefix("cursor=")
        ?.replaceAfter("&", "")
        ?.removeSuffix("&")
        ?.replaceAfter(">", "")
        ?.removeSuffix(">")
        ?.let { nextPageChannel.send(it) }
}

private fun randomRestaurant(restaurants: List<Restaurant>): Restaurant {
    return restaurants.random()
}

suspend fun monitorBackgroundThreads(
    channel: Channel<List<Restaurant>>,
    updateChannel: Channel<BaseUpdater>,
    //strictCheck: Boolean
) {
    for (restaurants in channel) {
        updateChannel.send(AddRestaurantsUpdater(restaurants))//, strictCheck))
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
    eventChannel: Channel<BaseEvent>,
    //strictCheck: Boolean
) {
    updateChannel.send(AddRestaurantsUpdater(restaurants))//, strictCheck))
    eventChannel.send(FinishedLoadingNewRestaurantsEvent())
}

suspend fun startQueryingFoursquare(
    state: RandomizerState,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>,
    channel: Channel<List<Restaurant>>
) {
    val job = GlobalScope.launch { queryFoursquare(state, channel, updateChannel, eventChannel) }
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
    eventChannel.send(FoursquareQueryErrorEvent())
    throwRestaurantError(state, updateChannel, eventChannel)
}

private suspend fun throwRestaurantError(
    state: RandomizerState,
    updateChannel: Channel<BaseUpdater>,
    eventChannel: Channel<BaseEvent>
) {
    updateChannel.send(CurrRestaurantUpdater(state.currRestaurant))
    eventChannel.send(LoadThumbnailEvent(state.currRestaurant?.imageUrl))
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
    eventChannel.send(LoadThumbnailEvent(restaurant.imageUrl))
    restaurant.coordinates.run {
        mapChannel.send(MapEvent(latitude, longitude, true))
    }
    updateChannel.send(HistoryUpdater(restaurant))
}

fun isBlocked(prevState: RandomizerState, restaurant: Restaurant) =
    prevState.blockList.contains(restaurant)

fun isClosed(restaurant: Restaurant): Boolean {//, strictCheck: Boolean): Boolean {
    val currentDate: Calendar = Calendar.getInstance()
    currentDate.add(Calendar.YEAR, -1)
    return restaurant.likelyClosed //|| (strictCheck && restaurant.latestInteraction?.let { currentDate.time.after(it) } ?: true)
}

fun tooFewReviews(restaurant: Restaurant, strictCheck: Boolean) = strictCheck && restaurant.ratingCount == 0L

fun isRecentlySeen(prevState: RandomizerState, restaurant: Restaurant) =
    prevState.restaurantsSeenRecently.contains(restaurant.id)

fun isTooFar(prevState: RandomizerState, restaurant: Restaurant) =
    milesToMeters(prevState.maxMilesSelected).roundToInt() < (restaurant.distance ?: Integer.MAX_VALUE)

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

fun fastFoodCheck(state: RandomizerState, restaurant: Restaurant): Boolean =
    !state.fastFoodSelected || restaurant.categories.any { it.name.contains("Fast Food") }

fun dietCheck(state: RandomizerState, restaurant: Restaurant): Boolean =
    state.diet == Diet.None || restaurant.categories.any { it.name.contains(state.diet.identifier, ignoreCase = true) }

fun isRestaurantFiltered(state: RandomizerState, restaurant: Restaurant) =//, strictCheck: Boolean = true) =
    isTooFar(state, restaurant)
        || !favoriteCheck(state, restaurant)
        || isTooLowRating(state, restaurant)
        || isClosed(restaurant)//, strictCheck)
//        || tooFewReviews(restaurant, strictCheck)
        || !fastFoodCheck(state, restaurant)
        || !dietCheck(state, restaurant)

fun isValidRestaurant(state: RandomizerState, restaurant: Restaurant) = //, strictCheck: Boolean = true) =
    !(isRecentlySeen(state, restaurant)
        || isBlocked(state, restaurant)
        || isRestaurantFiltered(state, restaurant)//, strictCheck)
        )
