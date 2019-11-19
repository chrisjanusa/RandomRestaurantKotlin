package com.chrisjanusa.randomizer.actions

import com.chrisjanusa.randomizer.models.RandomizerState

interface BaseUpdater {
    fun performUpdate(prevState : RandomizerState) : RandomizerState
}