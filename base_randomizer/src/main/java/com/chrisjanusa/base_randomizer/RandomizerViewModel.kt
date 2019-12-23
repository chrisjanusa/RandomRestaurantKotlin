package com.chrisjanusa.base_randomizer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RandomizerViewModel : ViewModel() {
    val state = MutableLiveData<RandomizerState>(RandomizerState())
    private val updateChannel = Channel<BaseUpdater>(Channel.UNLIMITED)
    val eventChannel = Channel<BaseEvent>(Channel.UNLIMITED)
    init {
        viewModelScope.launch {
            monitorChannel()
        }
    }

    private suspend fun update(updater: BaseUpdater) {
        withContext(Dispatchers.Main) {
            state.value?.let {
                state.value = updater.performUpdate(it)
            }
        }
    }

    private suspend fun monitorChannel() {
        for (updater in updateChannel) {
            update(updater)
        }
    }

    suspend fun performAction(action : BaseAction) {
        action.performAction(state, updateChannel, eventChannel)
    }
}