package com.chrisjanusa.randomizer.yelp

import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.toYelpString
import com.chrisjanusa.randomizer.filter_diet.DietHelper
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper
import com.chrisjanusa.yelp.YelpRepository
import com.chrisjanusa.yelp.models.Restaurant
import kotlin.math.roundToInt

object YelpHelper {
    suspend fun queryYelp(state: RandomizerState) : List<Restaurant> {
        state.run {
            val categories = when {
                cuisineSet.isNotEmpty() -> cuisineSet.toYelpString()
                diet != DietHelper.Diet.None -> diet.identifier
                else -> null
            }

            var term = "restaurants"
            term = if (cuisineSet.isNotEmpty() && diet != DietHelper.Diet.None) "${diet.identifier} $term" else term

            val radius = DistanceHelper.milesToMeters(maxMilesSelected).roundToInt()

            val price = null
            return YelpRepository.getBusinessSearchResults(
                latitude = currLat,
                longitude = currLng,
                term = term,
                radius = radius,
                categories = categories,
                offset = null,
                price = price,
                limit = 2,
                open_now = openNowSelected
            ).businesses
        }
    }

    fun randomRestaurant(restaurants: List<Restaurant>) : Restaurant {
        return restaurants.random()
    }
}