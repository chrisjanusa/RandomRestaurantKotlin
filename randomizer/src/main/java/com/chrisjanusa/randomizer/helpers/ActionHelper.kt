package com.chrisjanusa.randomizer.helpers

import com.chrisjanusa.randomizer.actions.BaseAction
import com.chrisjanusa.randomizer.models.RandomizerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ActionHelper {
    fun sendAction(action: BaseAction, randomizerViewModel: RandomizerViewModel) {
        GlobalScope.launch {
            randomizerViewModel.performAction(action)
        }
    }
}