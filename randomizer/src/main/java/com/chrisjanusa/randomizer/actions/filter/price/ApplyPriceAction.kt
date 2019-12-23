package com.chrisjanusa.randomizer.actions.filter.price

import androidx.lifecycle.LiveData
import com.chrisjanusa.base_filters.fragment.filters.FilterOpenUpdater
import com.chrisjanusa.base_filters.fragment.events.CloseFilterEvent
import com.chrisjanusa.randomizer.helpers.PriceHelper.Price
import com.chrisjanusa.randomizer.helpers.PriceHelper.priceToDisplayString
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class ApplyPriceAction : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
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