package com.chrisjanusa.randomizer.yelp.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.updaters.LocationUpdater
import com.chrisjanusa.yelp.YelpRepository
import kotlinx.coroutines.channels.Channel

class RandomizeAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.run {
            println("Lat/Lng $currLat,$currLng")
            val searchResults = YelpRepository.getBusinessSearchResults(currLat, currLng)
            searchResults.businesses[0].coordinates.run {
                LocationUpdater(
                    locationText,
                    latitude,
                    longitude
                )
                mapChannel.send(MapUpdate(latitude,longitude,true))
            }
        }
    }
}