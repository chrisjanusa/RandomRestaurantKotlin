package com.chrisjanusa.base

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

object CommunicationHelper {
    fun sendAction(action: BaseAction, randomizerViewModel: RandomizerViewModel) {
        GlobalScope.launch {
            randomizerViewModel.performAction(action)
        }
    }

    fun sendEvent(event: BaseEvent, eventChannel: Channel<BaseEvent>) {
        GlobalScope.launch {
            eventChannel.send(event)
        }
    }

    fun sendUpdate(updater: BaseUpdater, updateChannel: Channel<BaseUpdater>) {
        GlobalScope.launch {
            updateChannel.send(updater)
        }
    }

    fun sendMap(coordinates: MapUpdate, mapChannel: Channel<MapUpdate>) {
        GlobalScope.launch {
            mapChannel.send(coordinates)
        }
    }

    fun getViewModel(fragActivity: FragmentActivity) =
        ViewModelProvider(fragActivity).get(RandomizerViewModel::class.java)

}