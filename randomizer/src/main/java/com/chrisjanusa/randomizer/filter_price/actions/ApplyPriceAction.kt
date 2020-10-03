package com.chrisjanusa.randomizer.filter_price.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_base.events.CloseFilterEvent
import com.chrisjanusa.randomizer.filter_base.updaters.FilterOpenUpdater
import com.chrisjanusa.randomizer.filter_price.updaters.SelectedPriceUpdater
import kotlinx.coroutines.channels.Channel

class ApplyPriceAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        currentState.value?.run {
            if (priceSet != priceTempSet)
                updateChannel.send(SelectedPriceUpdater(priceTempSet))
        }
        eventChannel.send(CloseFilterEvent())
        updateChannel.send(FilterOpenUpdater(null))
    }
}