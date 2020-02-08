package com.chrisjanusa.randomizer.filter_cuisine.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.Cuisine
import com.chrisjanusa.randomizer.filter_cuisine.updaters.TempCuisineAddUpdater
import com.chrisjanusa.randomizer.filter_cuisine.updaters.TempCuisineRemoveUpdater
import kotlinx.coroutines.channels.Channel

class CuisineChangeAction(private val cuisine: Cuisine) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.cuisineTempSet?.let {
            val curr = HashSet(it)
            val updater =
                if (curr.contains(cuisine)) TempCuisineRemoveUpdater(cuisine) else TempCuisineAddUpdater(cuisine)
            updateChannel.send(updater)
        }
    }

}