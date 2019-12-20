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
        val update = currentState.value?.run {
            when (priceChanged) {
                1 -> Price1Updater(!price1TempSelected)
                2 -> Price2Updater(!price2TempSelected)
                3 -> Price3Updater(!price3TempSelected)
                4 -> Price4Updater(!price4TempSelected)
                else -> null
            }
        }

        update?.let{
            updateChannel.send(it)
        }
    }

}