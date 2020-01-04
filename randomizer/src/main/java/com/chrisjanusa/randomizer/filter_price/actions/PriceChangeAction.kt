package com.chrisjanusa.randomizer.filter_price.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_price.PriceHelper.Price
import com.chrisjanusa.randomizer.filter_price.updaters.TempPriceAddUpdater
import com.chrisjanusa.randomizer.filter_price.updaters.TempPriceRemoveUpdater
import kotlinx.coroutines.channels.Channel

class PriceChangeAction(private val price: Price) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.priceTempSet?.let {
            val curr = HashSet(it)
            val updater =
                if (curr.contains(price)) TempPriceRemoveUpdater(price) else TempPriceAddUpdater(price)
            updateChannel.send(updater)
        }
    }

}