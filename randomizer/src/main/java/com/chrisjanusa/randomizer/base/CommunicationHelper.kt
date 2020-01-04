package com.chrisjanusa.randomizer.base

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
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
        ViewModelProviders.of(fragActivity)[RandomizerViewModel::class.java]

}