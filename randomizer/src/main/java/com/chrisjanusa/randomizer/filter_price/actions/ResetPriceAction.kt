package com.chrisjanusa.randomizer.filter_price.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_price.updaters.Price1Updater
import com.chrisjanusa.randomizer.filter_price.updaters.Price2Updater
import com.chrisjanusa.randomizer.filter_price.updaters.Price3Updater
import com.chrisjanusa.randomizer.filter_price.updaters.Price4Updater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.MapUpdate
import kotlinx.coroutines.channels.Channel

class ResetPriceAction  : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.run {
            updateChannel.send(Price1Updater(false))
            updateChannel.send(Price2Updater(false))
            updateChannel.send(Price3Updater(false))
            updateChannel.send(Price4Updater(false))
        }
    }
}