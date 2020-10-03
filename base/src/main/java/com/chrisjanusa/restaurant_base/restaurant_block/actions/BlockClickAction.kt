package com.chrisjanusa.restaurant_base.restaurant_block.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.restaurant_base.restaurant_block.updaters.BlockUpdater
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.channels.Channel


open class BlockClickAction(private val restaurant: Restaurant) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        updateChannel.send(BlockUpdater(restaurant))
    }
}