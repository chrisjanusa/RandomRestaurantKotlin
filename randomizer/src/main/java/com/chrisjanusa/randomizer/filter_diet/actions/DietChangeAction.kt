package com.chrisjanusa.randomizer.filter_diet.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_diet.DietHelper.Diet
import com.chrisjanusa.randomizer.filter_diet.updaters.TempDietUpdater
import kotlinx.coroutines.channels.Channel

class DietChangeAction(private val dietChanged: Diet) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        val newDietSelection =
            dietChanged.takeUnless { it == currentState.value?.dietTempSelected } ?: Diet.None
        updateChannel.send(TempDietUpdater(newDietSelection))
    }

}