package com.chrisjanusa.randomizer.actions.gpsActions

import android.app.Activity
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.helpers.LocationHelper.requestLocation
import com.chrisjanusa.randomizer.models.RandomizerState
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import kotlinx.coroutines.channels.Channel

class PermissionReceivedAction(private val activity: Activity, private val randomizerViewModel: RandomizerViewModel) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        requestLocation(activity, randomizerViewModel)
    }
}