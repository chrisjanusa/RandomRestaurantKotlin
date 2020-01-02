package com.chrisjanusa.randomizer.location_search.actions

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.location_base.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationUpdater
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLat
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLng
import com.chrisjanusa.randomizer.location_search.updaters.LastManualLocationUpdater
import com.seatgeek.placesautocomplete.model.AddressComponentType
import com.seatgeek.placesautocomplete.model.PlaceDetails
import kotlinx.coroutines.channels.Channel

class LocationChosenAction(private val details: PlaceDetails, private val context: Context) :
    BaseAction {
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
            val address = Geocoder(context).getFromLocationName(formatted_address, 1)
                .getOrNull(0)
            location.latitude = address?.latitude ?: defaultLat
            location.longitude = address?.longitude ?: defaultLng

            updateChannel.send(LocationUpdater(text, location))
            updateChannel.send(LastManualLocationUpdater(text, location))
            updateChannel.send(GpsStatusUpdater(false))
        }
    }
}