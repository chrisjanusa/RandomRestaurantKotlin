package com.chrisjanusa.base_randomizer

import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.Channel

interface BaseAction {
    suspend fun performAction(currentState : LiveData<RandomizerState>, updateChannel: Channel<BaseUpdater>, eventChannel: Channel<BaseEvent>)
}