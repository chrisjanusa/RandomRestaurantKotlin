package com.chrisjanusa.randomizer.yelp.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.updaters.LocationUpdater
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.toYelpString
import com.chrisjanusa.randomizer.filter_diet.DietHelper.Diet
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.milesToMeters
import com.chrisjanusa.yelp.YelpRepository
import kotlinx.coroutines.channels.Channel
import kotlin.math.roundToInt

class RandomizeAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.run {
            val categories = when {
                cuisineSet.isNotEmpty() -> cuisineSet.toYelpString()
                diet != Diet.None -> diet.identifier
                else -> null
            }

            var term = "restaurants"
            term = if (cuisineSet.isNotEmpty() && diet != Diet.None) "${diet.identifier} $term" else term

            val radius = milesToMeters(maxMilesSelected).roundToInt()

            val price = null
            val searchResults = YelpRepository.getBusinessSearchResults(
                latitude = currLat,
                longitude = currLng,
                term = term,
                radius = radius,
                categories = categories,
                offset = null,
                price = price,
                open_now = true
            )
            searchResults.businesses[0].coordinates.run {
                LocationUpdater(
                    locationText,
                    latitude,
                    longitude
                )
                mapChannel.send(MapUpdate(latitude, longitude, true))
            }
        }
    }
}