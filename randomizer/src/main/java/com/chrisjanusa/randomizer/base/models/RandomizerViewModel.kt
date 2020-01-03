package com.chrisjanusa.randomizer.base.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RandomizerViewModel : ViewModel() {
    val state = MutableLiveData<RandomizerState>(RandomizerState())
    private val updateChannel = Channel<BaseUpdater>(Channel.UNLIMITED)
    val eventChannel = Channel<BaseEvent>(Channel.UNLIMITED)
    val mapChannel = Channel<MapUpdate>(Channel.CONFLATED)

    init {
        viewModelScope.launch {
            monitorChannel()
        }
    }

    private suspend fun monitorChannel() {
        for (updater in updateChannel) {
            withContext(Dispatchers.Main) {
                state.value?.let {
                    state.value = updater.performUpdate(it)
                }
            }
        }
    }

    suspend fun performAction(action : BaseAction) {
        action.performAction(state, updateChannel, eventChannel, mapChannel)
    }
}