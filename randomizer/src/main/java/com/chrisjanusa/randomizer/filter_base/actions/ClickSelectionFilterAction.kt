package com.chrisjanusa.randomizer.filter_base.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_base.events.CloseFilterEvent
import com.chrisjanusa.randomizer.filter_base.FilterHelper
import com.chrisjanusa.randomizer.filter_base.updaters.FilterOpenUpdater
import com.chrisjanusa.randomizer.filter_base.events.OpenFilterEvent
import com.chrisjanusa.randomizer.base.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class ClickSelectionFilterAction(val filter : FilterHelper.Filter) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        currentState.value?.run {
            if (filterOpen != filter) {
                updateChannel.send(FilterOpenUpdater(filter))
                filter.fragment?.let{
                    eventChannel.send(OpenFilterEvent(it))
                }
            } else {
                eventChannel.send(CloseFilterEvent())
                updateChannel.send(FilterOpenUpdater(FilterHelper.Filter.None))
            }
        }
    }
}