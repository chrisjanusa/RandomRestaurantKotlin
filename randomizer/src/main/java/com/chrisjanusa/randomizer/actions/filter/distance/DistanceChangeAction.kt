package com.chrisjanusa.randomizer.actions.filter.distance

import androidx.lifecycle.LiveData
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class DistanceChangeAction(private val dist : Float) : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        updateChannel.send(TempDistanceUpdater(dist))
    }
}