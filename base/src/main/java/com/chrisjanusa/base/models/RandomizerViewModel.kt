package com.chrisjanusa.base.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


class RandomizerViewModel : ViewModel() {
    val state = MutableLiveData<RandomizerState>(RandomizerState())
    private val updateChannel = Channel<BaseUpdater>(Channel.UNLIMITED)
    val eventChannel = Channel<BaseEvent>(Channel.UNLIMITED)
    val mapChannel = Channel<MapEvent>(Channel.CONFLATED)

    init {
        viewModelScope.launch {
            monitorChannel()
        }
    }

    private suspend fun monitorChannel() {
        for (updater in updateChannel) {
            state.value?.let {
                state.value = updater.performUpdate(it)
            }
        }
    }

    suspend fun performAction(action: BaseAction) {
        action.performAction(state, updateChannel, eventChannel, mapChannel)
    }
}