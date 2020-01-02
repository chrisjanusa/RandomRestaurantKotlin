package com.chrisjanusa.randomizer.filter_price.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_price.PriceHelper
import com.chrisjanusa.randomizer.filter_price.PriceHelper.Price
import com.chrisjanusa.randomizer.filter_price.updaters.InitPriceFilterUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class InitPriceFilterAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        val currPriceList = PriceHelper.priceFromDisplayString(currentState.value?.priceText ?: PriceHelper.defaultPriceTitle)
         updateChannel.send(
             InitPriceFilterUpdater(
                 currPriceList.contains(Price.One), currPriceList.contains(Price.Two),
                 currPriceList.contains(Price.Three), currPriceList.contains(Price.Four)
             )
         )
    }

}