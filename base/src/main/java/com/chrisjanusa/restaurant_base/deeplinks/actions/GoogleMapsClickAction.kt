package com.chrisjanusa.restaurant_base.deeplinks.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.restaurant_base.deeplinks.events.OpenGoogleMapsEvent
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