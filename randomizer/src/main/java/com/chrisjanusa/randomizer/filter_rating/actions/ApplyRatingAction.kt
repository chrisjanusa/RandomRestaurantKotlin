package com.chrisjanusa.randomizer.filter_rating.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_base.events.CloseFilterEvent
import com.chrisjanusa.randomizer.filter_base.updaters.FilterOpenUpdater
import com.chrisjanusa.randomizer.filter_rating.updaters.SelectedRatingUpdater
import kotlinx.coroutines.channels.Channel

class ApplyRatingAction : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        currentState.value?.run {
            if (tempMinRating != minRating)
                updateChannel.send(SelectedRatingUpdater(tempMinRating))
        }
        eventChannel.send(CloseFilterEvent())
        updateChannel.send(FilterOpenUpdater(null))
    }
}