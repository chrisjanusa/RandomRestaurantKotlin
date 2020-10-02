package com.chrisjanusa.randomizer.filter_distance.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_distance.updaters.TempDistanceUpdater
import kotlinx.coroutines.channels.Channel

class InitDistanceFilterAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.run {
            updateChannel.send(TempDistanceUpdater(maxMilesSelected))
        }
    }

}