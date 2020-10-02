package com.chrisjanusa.randomizer.location_gps.events

import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment

class PermissionEvent(private val permissions: Array<String>, private val permissionNum: Int) :
    BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.requestPermissions(permissions, permissionNum)
    }

}