package com.chrisjanusa.randomizer.actions.filter.category

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.helpers.CategoryHelper.Category
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class CategoryChangeAction(private val category: Category) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {

        currentState.value?.categoryTempSet?.let {
            val curr = HashSet(it)
            if (curr.contains(category)) {
                curr.remove(category)
            }
            else{
                curr.add(category)
            }
            updateChannel.send(TempCategoryUpdater(curr))
        }
    }

}