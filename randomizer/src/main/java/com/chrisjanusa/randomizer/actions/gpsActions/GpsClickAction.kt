package com.chrisjanusa.randomizer.actions.gpsActions

import android.app.Activity
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.helpers.LocationHelper.requestLocation
import com.chrisjanusa.randomizer.models.RandomizerState
import com.chrisjanusa.randomizer.models.RandomizerViewModel
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
                updateChannel.send(LocationUpdater("Unknown", Location("")))
            } else {
                updateChannel.send(GpsStatusUpdater(true))
                updateChannel.send(LocationUpdater("Locating", Location("")))
                requestLocation(activity, randomizerViewModel)
            }
        }
    }
}