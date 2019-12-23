package com.chrisjanusa.randomizer.actions.gpsActions

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class LocationReceivedAction(private val location: Location, private val context: Context) :
    com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        val locationName = Geocoder(context)
            .getFromLocation(location.latitude, location.longitude, 1)[0]
            .locality
        updateChannel.send(LocationUpdater(locationName, location))
    }
}