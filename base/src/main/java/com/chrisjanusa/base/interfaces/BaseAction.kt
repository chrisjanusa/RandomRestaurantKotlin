package com.chrisjanusa.base.interfaces

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerState
import kotlinx.coroutines.channels.Channel

interface BaseAction {
    suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    )
}