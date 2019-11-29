package com.chrisjanusa.randomizer.helpers

import com.chrisjanusa.randomizer.actions.base.BaseAction
import com.chrisjanusa.randomizer.events.BaseEvent
import com.chrisjanusa.randomizer.models.RandomizerViewModel
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