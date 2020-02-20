package com.chrisjanusa.randomizer.deeplinks.events

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.deeplinks.DeeplinkHelper.openDeeplink

class OpenYelpEvent(private val url: String) : BaseEvent{
    override fun handleEvent(fragment: RandomizerFragment) {
        fragment.context?.let {  openDeeplink(url, it) }
    }
}