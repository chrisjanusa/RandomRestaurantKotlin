package com.chrisjanusa.randomizer.filter_rating.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_distance.updaters.TempDistanceUpdater
import com.chrisjanusa.randomizer.filter_rating.updaters.TempRatingUpdater
import kotlinx.coroutines.channels.Channel

class RatingChangeAction(private val rating: Float) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        updateChannel.send(TempRatingUpdater(rating))
    }
}