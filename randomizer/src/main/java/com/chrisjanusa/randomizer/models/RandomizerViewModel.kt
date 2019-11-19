package com.chrisjanusa.randomizer.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chrisjanusa.randomizer.actions.BaseAction
import com.chrisjanusa.randomizer.actions.BaseUpdater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RandomizerViewModel : ViewModel() {
    val state = MutableLiveData<RandomizerState>(RandomizerState())
    private val channel = Channel<BaseUpdater>(Channel.UNLIMITED)
    init {
        GlobalScope.launch {
            monitorChannel()
        }
    }

    private suspend fun update(updater: BaseUpdater) {
        withContext(Dispatchers.Main) {
            state.value = updater.performUpdate(state.value!!)
        }
    }

    private suspend fun monitorChannel() {
        for (updater in channel) {
            update(updater)
        }
    }

    suspend fun performAction(action : BaseAction) {
        action.performAction(state, channel)
    }
}