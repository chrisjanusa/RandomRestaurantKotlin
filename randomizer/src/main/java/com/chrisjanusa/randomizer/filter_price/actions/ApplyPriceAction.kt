package com.chrisjanusa.randomizer.filter_price.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.events.CloseFilterEvent
import com.chrisjanusa.randomizer.filter_base.updaters.FilterOpenUpdater
import com.chrisjanusa.randomizer.filter_price.PriceHelper.toSaveString
import com.chrisjanusa.randomizer.filter_price.updaters.SelectedPriceUpdater
import kotlinx.coroutines.channels.Channel

class ApplyPriceAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.run {
            updateChannel.send(SelectedPriceUpdater(priceTempSet.toSaveString()))
        }
        eventChannel.send(CloseFilterEvent())
        updateChannel.send(FilterOpenUpdater(Filter.None))
    }
}