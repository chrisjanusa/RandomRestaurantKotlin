package com.chrisjanusa.randomizer.actions.filter.price

import androidx.lifecycle.LiveData
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class ResetPriceAction  : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        currentState.value?.run {
            updateChannel.send(Price1Updater(false))
            updateChannel.send(Price2Updater(false))
            updateChannel.send(Price3Updater(false))
            updateChannel.send(Price4Updater(false))
        }
    }
}