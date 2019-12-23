package com.chrisjanusa.randomizer.actions.filter.restriction

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.helpers.RestrictionHelper.Restriction
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class RestrictionChangeAction(private val restrictionChanged: Restriction) : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        val newRestrictionSelection =
            restrictionChanged.takeUnless { it == currentState.value?.restrictionTempSelected } ?: Restriction.None
        updateChannel.send(TempRestrictionUpdater(newRestrictionSelection))
    }

}