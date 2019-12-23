package com.chrisjanusa.base_filters.fragment.filters

import androidx.lifecycle.LiveData
import com.chrisjanusa.base_filters.fragment.events.CloseFilterEvent
import com.chrisjanusa.base_filters.fragment.helpers.FilterHelper
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class CancelClickAction : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        eventChannel.send(CloseFilterEvent())
        updateChannel.send(FilterOpenUpdater(FilterHelper.Filter.None))
    }
}