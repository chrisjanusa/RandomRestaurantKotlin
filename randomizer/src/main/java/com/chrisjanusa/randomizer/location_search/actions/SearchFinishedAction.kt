package com.chrisjanusa.randomizer.location_search.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_search.updaters.SearchStringUpdater
import com.chrisjanusa.randomizer.location_search.events.CloseSearchEvent
import kotlinx.coroutines.channels.Channel

class SearchFinishedAction(private val addressSearchString: String) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        eventChannel.send(CloseSearchEvent())
        updateChannel.send(SearchStringUpdater(addressSearchString))
    }
}