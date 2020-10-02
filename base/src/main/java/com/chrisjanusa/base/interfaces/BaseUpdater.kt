package com.chrisjanusa.base.interfaces

import com.chrisjanusa.base.models.RandomizerState

interface BaseUpdater {
    fun performUpdate(prevState: RandomizerState): RandomizerState
}