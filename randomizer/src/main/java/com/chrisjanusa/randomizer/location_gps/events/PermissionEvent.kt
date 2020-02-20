package com.chrisjanusa.randomizer.location_gps.events

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent

class PermissionEvent(private val permissions: Array<String>, private val permissionNum: Int) :
    BaseEvent {
    override fun handleEvent(fragment: RandomizerFragment) {
        fragment.requestPermissions(permissions, permissionNum)
    }

}