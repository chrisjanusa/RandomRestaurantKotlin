package com.chrisjanusa.randomizer.actions.permissionActions

import com.chrisjanusa.randomizer.actions.BaseUpdater
import com.chrisjanusa.randomizer.models.RandomizerState

class PermissionRequestUpdater (private val needPermision : Boolean) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(askForPermission = needPermision)
    }
}