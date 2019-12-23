package com.chrisjanusa.randomizer.actions.init

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.helpers.PriceHelper
import com.chrisjanusa.randomizer.helpers.PriceHelper.Price
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class InitPriceFilterAction : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        val currPriceList = PriceHelper.priceFromDisplayString(currentState.value?.priceText ?: PriceHelper.defaultPriceTitle)
         updateChannel.send(InitPriceFilterUpdater(currPriceList.contains(Price.One), currPriceList.contains(Price.Two),
             currPriceList.contains(Price.Three), currPriceList.contains(Price.Four)))
    }

}