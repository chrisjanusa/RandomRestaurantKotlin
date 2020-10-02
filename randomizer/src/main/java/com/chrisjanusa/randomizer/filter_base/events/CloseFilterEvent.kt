package com.chrisjanusa.randomizer.filter_base.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_base.OverlayFragmentManager

class CloseFilterEvent : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        (fragment.activity as OverlayFragmentManager).onFilterClosed()
    }
}