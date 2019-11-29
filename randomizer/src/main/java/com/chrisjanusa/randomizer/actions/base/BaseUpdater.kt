package com.chrisjanusa.randomizer.actions.base

import com.chrisjanusa.randomizer.models.RandomizerState

interface BaseUpdater {
    fun performUpdate(prevState : RandomizerState) : RandomizerState
}