package com.chrisjanusa.randomizer.events

import androidx.fragment.app.Fragment

interface BaseEvent {
    fun handleEvent(fragment: Fragment)
}