package com.chrisjanusa.randomizer.filter_category.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.filter_category.updaters.TempCategoryUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class ResetCategoryAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        currentState.value?.run {  updateChannel.send(
            TempCategoryUpdater(
                HashSet()
            )
        ) }
    }
}