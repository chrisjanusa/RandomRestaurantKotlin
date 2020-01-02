package com.chrisjanusa.randomizer.base.interfaces

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.models.RandomizerState
import kotlinx.coroutines.channels.Channel

interface BaseAction {
    suspend fun performAction(currentState : LiveData<RandomizerState>, updateChannel: Channel<BaseUpdater>, eventChannel: Channel<BaseEvent>)
}