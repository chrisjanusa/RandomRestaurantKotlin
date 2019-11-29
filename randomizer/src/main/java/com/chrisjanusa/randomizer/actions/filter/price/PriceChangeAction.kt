package com.chrisjanusa.randomizer.actions.filter.price

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class PriceChangeAction(val priceChanged : Int) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        val update = when(priceChanged) {
            1 -> Price1Updater(!currentState.value!!.price1TempSelected)
            2 -> Price2Updater(!currentState.value!!.price2TempSelected)
            3 -> Price3Updater(!currentState.value!!.price3TempSelected)
            4 -> Price4Updater(!currentState.value!!.price4TempSelected)
            else -> null
        }

        if (update != null) {
            updateChannel.send(update)
        }
    }

}