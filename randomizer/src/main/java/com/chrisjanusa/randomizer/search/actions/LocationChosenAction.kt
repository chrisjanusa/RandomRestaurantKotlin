package com.chrisjanusa.randomizer.search.actions

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.actions.gpsActions.GpsStatusUpdater
import com.chrisjanusa.randomizer.actions.gpsActions.LocationUpdater
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.models.RandomizerState
import com.seatgeek.placesautocomplete.model.AddressComponentType
import com.seatgeek.placesautocomplete.model.PlaceDetails
import kotlinx.coroutines.channels.Channel

class LocationChosenAction(private val details: PlaceDetails, private val context: Context) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        details.run {
            var text = "Error"
            for (component in address_components) {
                for (type in component.types) {
                    if (type == AddressComponentType.LOCALITY) {
                        text = component.long_name
                    }
                }
            }
            val location = Location("")
            val address = Geocoder(context).getFromLocationName(formatted_address, 1)[0]
            location.latitude = address.latitude
            location.longitude = address.longitude
            println("${address.latitude} ${address.longitude}")
            updateChannel.send(LocationUpdater(text, location))
            updateChannel.send(GpsStatusUpdater(false))
        }
    }
}