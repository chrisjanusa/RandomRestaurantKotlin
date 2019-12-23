package com.chrisjanusa.base_randomizer

interface BaseUpdater {
    fun performUpdate(prevState : RandomizerState) : RandomizerState
}