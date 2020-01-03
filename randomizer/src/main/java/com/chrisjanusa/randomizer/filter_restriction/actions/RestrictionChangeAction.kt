package com.chrisjanusa.randomizer.filter_restriction.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.filter_restriction.updaters.TempRestrictionUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper.Restriction
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.MapUpdate
import kotlinx.coroutines.channels.Channel

class RestrictionChangeAction(private val restrictionChanged: Restriction) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        val newRestrictionSelection =
            restrictionChanged.takeUnless { it == currentState.value?.restrictionTempSelected } ?: Restriction.None
        updateChannel.send(
            TempRestrictionUpdater(
                newRestrictionSelection
            )
        )
    }

}