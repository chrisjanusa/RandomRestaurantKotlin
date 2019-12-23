package com.chrisjanusa.randomizer.actions.filter.price

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.actions.filter.FilterOpenUpdater
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.events.CloseFilterEvent
import com.chrisjanusa.randomizer.helpers.FilterHelper
import com.chrisjanusa.randomizer.helpers.PriceHelper.Price
import com.chrisjanusa.randomizer.helpers.PriceHelper.priceToDisplayString
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class ApplyPriceAction :BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
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
        updateChannel.send(SelectedPriceUpdater(priceToDisplayString(selectedList)))
        eventChannel.send(CloseFilterEvent())
        updateChannel.send(FilterOpenUpdater(FilterHelper.Filter.None))
    }
}