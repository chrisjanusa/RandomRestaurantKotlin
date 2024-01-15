package com.chrisjanusa.randomizer.location_search.actions

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.hasLocationChanged
import com.chrisjanusa.randomizer.location_base.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationUpdater
import com.chrisjanusa.randomizer.location_search.events.LocationSelectedErrorEvent
import com.chrisjanusa.randomizer.location_search.updaters.LastManualLocationUpdater
import com.seatgeek.placesautocomplete.model.AddressComponentType
import com.seatgeek.placesautocomplete.model.PlaceDetails
import kotlinx.coroutines.channels.Channel


class LocationChosenAction(private val details: PlaceDetails, private val context: Context) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
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

            val address = Geocoder(context).getFromLocationName(formatted_address, 1)
                ?.getOrNull(0)
            val latitude = address?.latitude
            val longitude = address?.longitude
            if (latitude == null || longitude == null) {
                eventChannel.send(LocationSelectedErrorEvent(name))
                updateChannel.send(LastManualLocationUpdater(text))
                return
            }
            currentState.value?.run {
                if(hasLocationChanged(currLat, currLng, latitude, longitude)){
                    mapChannel.send(MapEvent(latitude, longitude, false))
                }
            }
            updateChannel.send(LocationUpdater(text, latitude, longitude))
            updateChannel.send(LastManualLocationUpdater(text))
            updateChannel.send(GpsStatusUpdater(false))
        }
    }
}