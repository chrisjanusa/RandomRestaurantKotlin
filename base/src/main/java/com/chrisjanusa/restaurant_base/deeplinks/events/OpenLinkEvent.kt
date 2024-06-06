package com.chrisjanusa.restaurant_base.deeplinks.events

import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.restaurant_base.deeplinks.DeeplinkHelper.openDeeplink

class OpenLinkEvent(private val url: String) : BaseEvent{
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let {  openDeeplink(url, it) }
    }
}