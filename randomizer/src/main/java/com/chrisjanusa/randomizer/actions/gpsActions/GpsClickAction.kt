package com.chrisjanusa.randomizer.actions.gpsActions

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.BaseAction
import com.chrisjanusa.randomizer.actions.BaseUpdater
import com.chrisjanusa.randomizer.helpers.LocationHelper
import com.chrisjanusa.randomizer.models.RandomizerState
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import kotlinx.coroutines.channels.Channel

class GpsClickAction(
    private val activity: Activity,
    private val randomizerViewModel: RandomizerViewModel
) : BaseAction {
    override suspend fun performAction(currentState: LiveData<RandomizerState>, channel: Channel<BaseUpdater>) {
        if (currentState.value!!.gpsOn) {
            channel.send(GpsStatusUpdater(false, "Unknown"))
        }
        else {
            LocationHelper.requestLocation(activity, randomizerViewModel)
            channel.send(GpsStatusUpdater(true, "Locating"))
        }
    }
}