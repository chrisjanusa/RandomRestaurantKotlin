package com.chrisjanusa.randomizer.location_gps.actions

import android.app.Activity
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.LocationHelper.calculateMapLat
import com.chrisjanusa.randomizer.location_base.LocationHelper.calculateMapLng
import com.chrisjanusa.randomizer.location_base.LocationHelper.calculatingLocationText
import com.chrisjanusa.randomizer.location_base.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationTextUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationUpdater
import com.chrisjanusa.randomizer.location_gps.GpsHelper.requestLocation
import kotlinx.coroutines.channels.Channel

class GpsClickAction(private val activity: Activity) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        currentState.value?.run {
            if (gpsOn) {
                updateChannel.send(GpsStatusUpdater(false))
                mapChannel.send(MapUpdate(calculateMapLat(lastManualLat), calculateMapLng(lastManualLng), false))
                updateChannel.send(LocationUpdater(lastManualLocationText, lastManualLat, lastManualLng))
            } else {
                updateChannel.send(GpsStatusUpdater(true))
                updateChannel.send(LocationTextUpdater(calculatingLocationText))
                requestLocation(activity, updateChannel, eventChannel, mapChannel)
            }
        }
    }
}