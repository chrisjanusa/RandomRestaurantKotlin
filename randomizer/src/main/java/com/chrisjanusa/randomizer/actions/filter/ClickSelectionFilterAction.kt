package com.chrisjanusa.randomizer.actions.filter

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.events.CloseFilterEvent
import com.chrisjanusa.randomizer.events.OpenFilterEvent
import com.chrisjanusa.randomizer.helpers.FilterHelper
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class ClickSelectionFilterAction(val filter : FilterHelper.Filter) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        currentState.value?.run {
            if (filterOpen != filter) {
                updateChannel.send(FilterOpenUpdater(filter))
                val filterFragment = FilterHelper.getFilterFragment(filter)
                if (filterFragment != null) {
                    eventChannel.send(OpenFilterEvent(filterFragment))
                }
            } else {
                eventChannel.send(CloseFilterEvent())
                updateChannel.send(FilterOpenUpdater(FilterHelper.Filter.None))
            }
        }
    }
}