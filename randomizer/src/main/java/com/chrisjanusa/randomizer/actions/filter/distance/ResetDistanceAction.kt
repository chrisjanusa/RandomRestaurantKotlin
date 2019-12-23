package com.chrisjanusa.randomizer.actions.filter.distance

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.helpers.DistanceHelper
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class ResetDistanceAction  : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        currentState.value?.run {  updateChannel.send(TempDistanceUpdater(DistanceHelper.defaultDistance)) }
    }
}