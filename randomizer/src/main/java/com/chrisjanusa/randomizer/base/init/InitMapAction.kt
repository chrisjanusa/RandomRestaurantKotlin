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
        currentState.value?.run {
            if (currLat == null || currLng == null) {
                mapChannel.send(MapUpdate(LocationHelper.spaceNeedleLat, LocationHelper.spaceNeedleLng, false))
            } else {
                mapChannel.send(MapUpdate(currLat, currLng, false))
            }
        }
    }

}
