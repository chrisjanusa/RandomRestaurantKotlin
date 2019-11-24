package com.chrisjanusa.randomizer.events

import androidx.fragment.app.Fragment

class PermissionEvent(val permissions : Array<String>, val permissionNum : Int) : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        fragment.requestPermissions(
            permissions,
            permissionNum
        )
    }

}