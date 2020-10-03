package com.chrisjanusa.randomizer.location_gps.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationTextUpdater
import kotlinx.coroutines.channels.Channel

class PermissionDeniedAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        updateChannel.send(GpsStatusUpdater(false))
        currentState.value?.run {
            updateChannel.send(LocationTextUpdater(lastManualLocationText))
        }
    }
}