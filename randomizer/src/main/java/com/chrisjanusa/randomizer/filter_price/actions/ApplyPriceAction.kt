package com.chrisjanusa.randomizer.filter_price.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.filter_base.updaters.FilterOpenUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_base.events.CloseFilterEvent
import com.chrisjanusa.randomizer.filter_price.updaters.SelectedPriceUpdater
import com.chrisjanusa.randomizer.filter_base.FilterHelper
import com.chrisjanusa.randomizer.filter_price.PriceHelper.Price
import com.chrisjanusa.randomizer.filter_price.PriceHelper.priceToDisplayString
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.MapUpdate
import kotlinx.coroutines.channels.Channel

class ApplyPriceAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        val selectedList = ArrayList<Price>()
        currentState.value?.run {
            if (price1TempSelected) {
                selectedList.add(Price.One)
            }
            if (price2TempSelected) {
                selectedList.add(Price.Two)
            }
            if (price3TempSelected) {
                selectedList.add(Price.Three)
            }
            if (price4TempSelected) {
                selectedList.add(Price.Four)
            }
        }
        updateChannel.send(
            SelectedPriceUpdater(
                priceToDisplayString(
                    selectedList
                )
            )
        )
        eventChannel.send(CloseFilterEvent())
        updateChannel.send(FilterOpenUpdater(FilterHelper.Filter.None))
    }
}