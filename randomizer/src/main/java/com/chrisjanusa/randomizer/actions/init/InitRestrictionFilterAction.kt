package com.chrisjanusa.randomizer.actions.init

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.filter.restriction.TempRestrictionUpdater
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class InitRestrictionFilterAction : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
         currentState.value?.run {  updateChannel.send(TempRestrictionUpdater(restriction)) }
    }

}