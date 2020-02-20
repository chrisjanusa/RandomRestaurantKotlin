package com.chrisjanusa.randomizer.base.init

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.LocationHelper
import kotlinx.coroutines.channels.Channel

class InitMapAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        println("Updating to init map")
        currentState.value?.run {
            if (currRestaurant != null) {
                println("Setting restaurant loc")
                mapChannel.send(MapUpdate(currRestaurant.coordinates.latitude, currRestaurant.coordinates.longitude, true))
            } else if (currLat == null || currLng == null) {
                println("Setting restaurant default")
                mapChannel.send(MapUpdate(LocationHelper.spaceNeedleLat, LocationHelper.spaceNeedleLng, false))
            } else {
                println("Setting restaurant user")
                mapChannel.send(MapUpdate(currLat, currLng, false))
            }
        }
    }

}
