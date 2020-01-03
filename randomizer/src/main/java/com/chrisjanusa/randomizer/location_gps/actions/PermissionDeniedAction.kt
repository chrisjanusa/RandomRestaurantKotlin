package com.chrisjanusa.randomizer.location_gps.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationTextUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import kotlinx.coroutines.channels.Channel

class PermissionDeniedAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        updateChannel.send(GpsStatusUpdater(false))
        currentState.value?.run {
            updateChannel.send(LocationTextUpdater(lastManualLocationText))
        }
    }
}