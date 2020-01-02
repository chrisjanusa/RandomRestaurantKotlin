package com.chrisjanusa.randomizer.location_gps.actions

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.location_shared.updaters.LocationUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class LocationReceivedAction(private val location: Location, private val context: Context) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        val locationName = Geocoder(context)
            .getFromLocation(location.latitude, location.longitude, 1)[0]
            .locality
        updateChannel.send(LocationUpdater(locationName, location))
    }
}