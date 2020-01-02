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