package com.chrisjanusa.randomizer.location_gps.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent

class PermissionEvent(val permissions : Array<String>, val permissionNum : Int) :
    BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        fragment.requestPermissions(
            permissions,
            permissionNum
        )
    }

}