package com.chrisjanusa.randomizer.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.filter_fragments.OverlayFragmentManager

class CloseFilterEvent :BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        (fragment.activity as OverlayFragmentManager).onFilterClosed()
    }
}