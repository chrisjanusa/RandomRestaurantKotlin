package com.chrisjanusa.baselist

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.restaurant_base.restaurant_block.actions.BlockClickAction
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.channels.Channel

class BlockClickAndRefreshAction(
    restaurant: Restaurant,
    private val position: Int,
    private val recyclerViewId: Int
) :
    BlockClickAction(restaurant) {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        super.performAction(currentState, updateChannel, eventChannel, mapChannel)
        eventChannel.send(RefreshCardEvent(position, recyclerViewId))
    }
}