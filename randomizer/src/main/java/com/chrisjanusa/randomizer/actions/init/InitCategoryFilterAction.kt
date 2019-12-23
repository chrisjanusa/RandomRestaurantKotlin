package com.chrisjanusa.randomizer.actions.init

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.filter.category.TempCategoryUpdater
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class InitCategoryFilterAction : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
         currentState.value?.run {updateChannel.send(TempCategoryUpdater(categorySet))}
    }

}