package com.chrisjanusa.randomizer.actions.filter.category

import androidx.lifecycle.LiveData
import com.chrisjanusa.base_filters.fragment.filters.FilterOpenUpdater
import com.chrisjanusa.base_filters.fragment.events.CloseFilterEvent
import com.chrisjanusa.randomizer.helpers.CategoryHelper.toSaveString
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class ApplyCategoryAction : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        currentState.value?.run {  updateChannel.send(SelectedCategoryUpdater(categoryTempSet, categoryTempSet.toSaveString())) }
        eventChannel.send(CloseFilterEvent())
        updateChannel.send(FilterOpenUpdater(FilterHelper.Filter.None))
    }
}