package com.chrisjanusa.randomizer.actions.base

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

interface BaseAction {
    suspend fun performAction(currentState : LiveData<RandomizerState>, updateChannel: Channel<BaseUpdater>, eventChannel: Channel<BaseEvent>)
}