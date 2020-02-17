package com.chrisjanusa.randomizer.yelp

import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.toYelpString
import com.chrisjanusa.randomizer.filter_diet.DietHelper
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.milesToMeters
import com.chrisjanusa.randomizer.filter_price.PriceHelper.setToYelpString
import com.chrisjanusa.yelp.YelpRepository
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

object YelpHelper {
    private const val restaurantsPerQuery = 50
    private const val numberOfRestaurants = 500

    suspend fun queryYelp(
        state: RandomizerState,
        channel: Channel<List<Restaurant>>
    ) {
        try {
            state.run {
                val categories = when {
                    cuisineSet.isNotEmpty() -> cuisineSet.toYelpString()
                    diet != DietHelper.Diet.None -> diet.identifier
                    else -> null
                }

                var term = "restaurants"
                term = if (cuisineSet.isNotEmpty() && diet != DietHelper.Diet.None) "${diet.identifier} $term" else term

                val radius = milesToMeters(maxMilesSelected).roundToInt()

                val price = setToYelpString(priceSet)
                for (i in 0 until numberOfRestaurants step restaurantsPerQuery) {
                    queryYelp(
                        latitude = currLat,
                        longitude = currLng,
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
            }
        } finally {
            channel.close()
        }
    }

    private suspend fun queryYelp(
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
        withContext(Dispatchers.Main) {
            println("Offset $offset is being run")
        }
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
        withContext(Dispatchers.Main) {
            println("Offset $offset finished")
        }
        channel.send(restaurants)
    }

    fun randomRestaurant(restaurants: List<Restaurant>): Restaurant {
        return restaurants.random()
    }
}