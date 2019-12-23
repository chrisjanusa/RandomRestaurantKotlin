package com.chrisjanusa.randomizer.actions.filter.distance

import androidx.lifecycle.LiveData
import com.chrisjanusa.base_filters.fragment.filters.FilterOpenUpdater
import com.chrisjanusa.base_filters.fragment.events.CloseFilterEvent
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class ApplyDistanceAction : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        currentState.value?.run {
            updateChannel.send(SelectedDistanceUpdater(tempMaxMiles))
        }
        eventChannel.send(CloseFilterEvent())
        updateChannel.send(FilterOpenUpdater(FilterHelper.Filter.None))
    }
}