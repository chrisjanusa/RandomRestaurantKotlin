package com.chrisjanusa.randomizer.deeplinks.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.deeplinks.events.OpenUberEvent
import com.chrisjanusa.yelp.models.Coordinates
import com.chrisjanusa.yelp.models.Location
import kotlinx.coroutines.channels.Channel

class UberClickAction(private val name: String, private val location: Location, private val coordinates: Coordinates) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        eventChannel.send(OpenUberEvent(name, location, coordinates))
    }
}