package com.chrisjanusa.randomizer.actions.init

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.helpers.FilterHelper
import com.chrisjanusa.randomizer.helpers.FilterHelper.Price
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class InitPriceFilterAction :BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        val currPriceList = FilterHelper.priceFromDisplayString(currentState.value?.priceText ?: FilterHelper.defaultPriceTitle)
         updateChannel.send(InitPriceFilterUpdater(currPriceList.contains(Price.One), currPriceList.contains(Price.Two),
             currPriceList.contains(Price.Three), currPriceList.contains(Price.Four)))
    }

}