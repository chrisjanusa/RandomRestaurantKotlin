package com.chrisjanusa.randomizer.base.interfaces

import com.chrisjanusa.randomizer.RandomizerFragment

interface BaseEvent {
    fun handleEvent(fragment: RandomizerFragment)
}