package com.chrisjanusa.base_randomizer

import androidx.fragment.app.Fragment

interface BaseEvent {
    fun handleEvent(fragment: Fragment)
}