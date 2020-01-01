package com.chrisjanusa.randomizer.search.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.models.RandomizerState
import com.chrisjanusa.randomizer.search.events.OpenSearchEvent
import kotlinx.coroutines.channels.Channel

class SearchGainFocusAction(private val addressSearchText : String) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        eventChannel.send(OpenSearchEvent(addressSearchText))
    }

}