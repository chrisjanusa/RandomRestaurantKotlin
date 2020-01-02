package com.chrisjanusa.randomizer.base

import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ActionHelper {
    fun sendAction(action: BaseAction, randomizerViewModel: RandomizerViewModel) {
        GlobalScope.launch {
            randomizerViewModel.performAction(action)
        }
    }

    fun sendEvent(event: BaseEvent, randomizerViewModel: RandomizerViewModel) {
        GlobalScope.launch {
            randomizerViewModel.eventChannel.send(event)
        }
    }
}