package com.chrisjanusa.base_filters.fragment.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.base_randomizer.BaseEvent
import com.chrisjanusa.base_filters.fragment.OverlayFragmentManager

class CloseFilterEvent : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        (fragment.activity as OverlayFragmentManager).onFilterClosed()
    }
}