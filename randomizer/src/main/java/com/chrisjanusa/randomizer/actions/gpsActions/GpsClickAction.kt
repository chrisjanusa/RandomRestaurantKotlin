package com.chrisjanusa.randomizer.actions.gpsActions

import android.app.Activity
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.helpers.LocationHelper.requestLocation
import com.chrisjanusa.base_randomizer.RandomizerState
import com.chrisjanusa.base_randomizer.RandomizerViewModel
import kotlinx.coroutines.channels.Channel

class GpsClickAction(
    private val activity: Activity,
    private val randomizerViewModel: RandomizerViewModel
) : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        currentState.value?.run {
            if (gpsOn) {
                updateChannel.send(GpsStatusUpdater(false, "Unknown"))
            } else {
                updateChannel.send(GpsStatusUpdater(true, "Locating"))
                requestLocation(activity, randomizerViewModel)
            }
        }
    }
}