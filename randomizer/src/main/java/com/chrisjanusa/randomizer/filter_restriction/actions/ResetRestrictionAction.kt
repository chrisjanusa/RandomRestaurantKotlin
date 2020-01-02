package com.chrisjanusa.randomizer.filter_restriction.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.filter_restriction.updaters.TempRestrictionUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper
import com.chrisjanusa.randomizer.base.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class ResetRestrictionAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        currentState.value?.run {  updateChannel.send(
            TempRestrictionUpdater(
                RestrictionHelper.Restriction.None
            )
        ) }
    }
}