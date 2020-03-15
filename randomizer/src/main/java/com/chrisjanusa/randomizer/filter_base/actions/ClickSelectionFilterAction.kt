package com.chrisjanusa.randomizer.filter_base.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.getFragFromFilter
import com.chrisjanusa.randomizer.filter_base.events.CloseFilterEvent
import com.chrisjanusa.randomizer.filter_base.events.OpenFilterEvent
import com.chrisjanusa.randomizer.filter_base.updaters.FilterOpenUpdater
import kotlinx.coroutines.channels.Channel

class ClickSelectionFilterAction(val filter: Filter) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.run {
            if (filterOpen != filter) {
                updateChannel.send(FilterOpenUpdater(filter))
                eventChannel.send(OpenFilterEvent(getFragFromFilter(filter)))
            } else {
                eventChannel.send(CloseFilterEvent())
                updateChannel.send(FilterOpenUpdater(null))
            }
        }
    }
}