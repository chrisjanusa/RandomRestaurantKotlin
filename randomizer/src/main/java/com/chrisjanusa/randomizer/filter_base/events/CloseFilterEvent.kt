package com.chrisjanusa.randomizer.filter_base.events

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_base.OverlayFragmentManager

class CloseFilterEvent : BaseEvent {
    override fun handleEvent(fragment: RandomizerFragment) {
        (fragment.activity as OverlayFragmentManager).onFilterClosed()
    }
}