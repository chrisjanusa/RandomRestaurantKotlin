package com.chrisjanusa.randomizer.filter_base.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.randomizer.filter_base.OverlayFragmentManager

class OpenFilterEvent(private val newFragment: Fragment) : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        (fragment.activity as OverlayFragmentManager).onFilterSelected(newFragment)
    }
}