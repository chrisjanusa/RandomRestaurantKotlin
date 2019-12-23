package com.chrisjanusa.randomizer.actions.filter.price

import androidx.lifecycle.LiveData
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class PriceChangeAction(val priceChanged : Int) : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
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