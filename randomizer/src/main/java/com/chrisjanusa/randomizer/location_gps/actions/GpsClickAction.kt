package com.chrisjanusa.randomizer.location_gps.actions

import android.app.Activity
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.location_shared.updaters.GpsStatusUpdater
import com.chrisjanusa.randomizer.location_shared.updaters.LocationUpdater
import com.chrisjanusa.randomizer.location_gps.LocationHelper.requestLocation
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
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
                        "Unknown",
                        Location("")
                    )
                )
            } else {
                updateChannel.send(GpsStatusUpdater(true))
                updateChannel.send(
                    LocationUpdater(
                        "Locating",
                        Location("")
                    )
                )
                requestLocation(activity, randomizerViewModel)
            }
        }
    }
}