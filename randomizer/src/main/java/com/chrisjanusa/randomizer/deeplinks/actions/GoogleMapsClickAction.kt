package com.chrisjanusa.randomizer.deeplinks.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.deeplinks.events.OpenGoogleMapsEvent
import com.chrisjanusa.yelp.models.Location
import kotlinx.coroutines.channels.Channel

class GoogleMapsClickAction(private val name: String, private val location: Location) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        eventChannel.send(OpenGoogleMapsEvent(name, location))
    }
}