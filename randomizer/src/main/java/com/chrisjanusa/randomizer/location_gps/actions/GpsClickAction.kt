package com.chrisjanusa.randomizer.location_gps.actions

import android.app.Activity
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.location_shared.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_shared.updaters.LocationUpdater
import com.chrisjanusa.randomizer.location_gps.GpsHelper.requestLocation
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.calculatingLocationText
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.defaultLocation
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.defaultLocationText
import kotlinx.coroutines.channels.Channel

class GpsClickAction(
    private val activity: Activity,
    private val randomizerViewModel: RandomizerViewModel
) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        currentState.value?.run {
            if (gpsOn) {
                updateChannel.send(GpsStatusUpdater(false))
                updateChannel.send(
                    LocationUpdater(
                        defaultLocationText,
                        defaultLocation
                    )
                )
            } else {
                updateChannel.send(GpsStatusUpdater(true))
                updateChannel.send(
                    LocationUpdater(
                        calculatingLocationText,
                        location
                    )
                )
                requestLocation(activity, randomizerViewModel)
            }
        }
    }
}