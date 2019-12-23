package com.chrisjanusa.base_filters.fragment.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.base_filters.fragment.OverlayFragmentManager
import com.chrisjanusa.base_randomizer.BaseEvent

class OpenFilterEvent (private val newFragment: Fragment) : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        (fragment.activity as OverlayFragmentManager).onFilterSelected(newFragment)
    }
}