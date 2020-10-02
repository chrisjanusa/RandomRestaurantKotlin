package com.chrisjanusa.randomizer.deeplinks.events

import androidx.fragment.app.Fragment
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.deeplinks.DeeplinkHelper.openDeeplink

class OpenYelpEvent(private val url: String) : BaseEvent{
    override fun handleEvent(fragment: Fragment) {
        fragment.context?.let {  openDeeplink(url, it) }
    }
}