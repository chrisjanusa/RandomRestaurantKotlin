package com.chrisjanusa.randomizer.filter_base.events

import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.filter_base.OverlayFragmentManager

class CloseFilterEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        (fragment.activity as OverlayFragmentManager).onFilterClosed()
    }
}