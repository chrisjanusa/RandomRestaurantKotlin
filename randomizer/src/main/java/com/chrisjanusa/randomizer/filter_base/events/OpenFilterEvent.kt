package com.chrisjanusa.randomizer.filter_base.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.filter_base.OverlayFragmentManager

class OpenFilterEvent(private val newFragment: Fragment) : BaseEvent {
    override fun handleEvent(fragment: RandomizerFragment) {
        (fragment.activity as OverlayFragmentManager).onFilterSelected(newFragment)
    }
}