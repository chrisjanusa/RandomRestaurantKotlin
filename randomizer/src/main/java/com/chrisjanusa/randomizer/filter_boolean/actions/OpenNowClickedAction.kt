package com.chrisjanusa.randomizer.filter_boolean.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.filter_boolean.updaters.OpenNowUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class OpenNowClickedAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        currentState.value?.run {
            updateChannel.send(OpenNowUpdater(!openNowSelected))
        }
    }
}