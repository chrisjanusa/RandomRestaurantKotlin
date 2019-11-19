package com.chrisjanusa.randomizer.actions.permissionActions

import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.actions.BaseAction
import com.chrisjanusa.randomizer.actions.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState
import kotlinx.coroutines.channels.Channel

class RequestPermissionAction : BaseAction {
    override suspend fun performAction(currentState: LiveData<RandomizerState>, channel: Channel<BaseUpdater>) {
        channel.send(PermissionRequestUpdater(true))
    }
}