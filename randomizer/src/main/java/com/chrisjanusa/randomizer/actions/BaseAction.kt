package com.chrisjanusa.randomizer.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

interface BaseAction {
    suspend fun performAction(currentState : LiveData<RandomizerState>, channel: Channel<BaseUpdater>)
}