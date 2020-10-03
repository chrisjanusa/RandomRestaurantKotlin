package com.chrisjanusa.randomizer.location_gps.actions

import android.app.Activity
import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.calculatingLocationText
import com.chrisjanusa.randomizer.location_base.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_base.updaters.LocationTextUpdater
import com.chrisjanusa.randomizer.location_gps.GpsHelper.requestLocation
import kotlinx.coroutines.channels.Channel

class GpsClickAction(private val activity: Activity) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        currentState.value?.run {
            if (gpsOn) {
                updateChannel.send(GpsStatusUpdater(false))
            } else {
                updateChannel.send(GpsStatusUpdater(true))
                updateChannel.send(LocationTextUpdater(calculatingLocationText))
                requestLocation(activity, updateChannel, eventChannel, mapChannel, currLat, currLng)
            }
        }
    }
}