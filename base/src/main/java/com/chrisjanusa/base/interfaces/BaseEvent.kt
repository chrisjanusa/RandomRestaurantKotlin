package com.chrisjanusa.base.interfaces

import androidx.fragment.app.Fragment

interface BaseEvent {
    fun handleEvent(fragment: Fragment)
}