package com.chrisjanusa.randomizer.helpers

import com.chrisjanusa.base_randomizer.RandomizerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ActionHelper {
    fun sendAction(action: com.chrisjanusa.base_randomizer.BaseAction, randomizerViewModel: RandomizerViewModel) {
        GlobalScope.launch {
            randomizerViewModel.performAction(action)
        }
    }

    fun sendEvent(event: com.chrisjanusa.base_randomizer.BaseEvent, randomizerViewModel: RandomizerViewModel) {
        GlobalScope.launch {
            randomizerViewModel.eventChannel.send(event)
        }
    }
}