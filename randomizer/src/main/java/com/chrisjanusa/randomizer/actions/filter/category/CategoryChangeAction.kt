package com.chrisjanusa.randomizer.actions.filter.category

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.helpers.CategoryHelper.Category
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class CategoryChangeAction(private val category: Category) : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
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