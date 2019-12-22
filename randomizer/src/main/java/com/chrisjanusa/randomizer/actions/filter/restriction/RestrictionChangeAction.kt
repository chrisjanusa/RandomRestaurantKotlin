package com.chrisjanusa.randomizer.actions.filter.restriction

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.helpers.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class RestrictionChangeAction(private val restrictionChanged: Restriction) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        val newRestrictionSelection =
            restrictionChanged.takeUnless { it == currentState.value?.restrictionTempSelected } ?: Restriction.None
        updateChannel.send(TempRestrictionUpdater(newRestrictionSelection))
    }

}