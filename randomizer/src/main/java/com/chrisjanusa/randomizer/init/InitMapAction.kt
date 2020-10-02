package com.chrisjanusa.randomizer.init

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.LocationHelper.initMapUpdate
import kotlinx.coroutines.channels.Channel

class InitMapAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.run {
            if (stateInitialized) {
                initMapUpdate(mapChannel, currRestaurant, currLat, currLng)
            }
        }
    }

}
