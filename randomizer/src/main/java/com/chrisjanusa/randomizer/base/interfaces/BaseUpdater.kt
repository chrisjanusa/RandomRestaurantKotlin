package com.chrisjanusa.randomizer.base.interfaces

import com.chrisjanusa.randomizer.base.models.RandomizerState

interface BaseUpdater {
    fun performUpdate(prevState: RandomizerState): RandomizerState
}