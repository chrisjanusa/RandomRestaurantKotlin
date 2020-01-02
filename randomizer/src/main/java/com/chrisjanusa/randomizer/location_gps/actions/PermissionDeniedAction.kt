package com.chrisjanusa.randomizer.location_gps.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationUpdater
import kotlinx.coroutines.channels.Channel

class PermissionDeniedAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        updateChannel.send(GpsStatusUpdater(false))
        currentState.value?.run {
            updateChannel.send(LocationUpdater(lastManualLocationText, lastManualLocation))
        }
    }
}