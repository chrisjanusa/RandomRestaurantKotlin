package com.chrisjanusa.randomizer.filter_category.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.filter_category.updaters.TempCategoryUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.MapUpdate
import kotlinx.coroutines.channels.Channel

class CategoryChangeAction(private val category: Category) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
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