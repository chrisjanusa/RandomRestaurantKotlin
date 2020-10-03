package com.chrisjanusa.randomizer.filter_diet.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.enums.Diet
import com.chrisjanusa.randomizer.filter_diet.updaters.TempDietUpdater
import kotlinx.coroutines.channels.Channel

class ResetDietAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        currentState.value?.run {
            updateChannel.send(TempDietUpdater(Diet.None))
        }
    }
}