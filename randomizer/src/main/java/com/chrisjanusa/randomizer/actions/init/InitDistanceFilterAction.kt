package com.chrisjanusa.randomizer.actions.init

import androidx.lifecycle.LiveData
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class InitDistanceFilterAction : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        currentState.value?.run {
            updateChannel.send(InitDistanceFilterUpdater(maxMilesSelected))
        }
    }

}