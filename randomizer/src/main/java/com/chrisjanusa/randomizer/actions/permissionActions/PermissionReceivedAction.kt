package com.chrisjanusa.randomizer.actions.permissionActions

import android.app.Activity
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.BaseAction
import com.chrisjanusa.randomizer.actions.BaseUpdater
import com.chrisjanusa.randomizer.helpers.LocationHelper
import com.chrisjanusa.randomizer.models.RandomizerState
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import kotlinx.coroutines.channels.Channel

class PermissionReceivedAction(private val activity: Activity, private val randomizerViewModel: RandomizerViewModel) :
    BaseAction {
    override suspend fun performAction(currentState: LiveData<RandomizerState>, channel: Channel<BaseUpdater>) {
        channel.send(PermissionRequestUpdater(false))
        LocationHelper.requestLocation(activity, randomizerViewModel)
    }
}