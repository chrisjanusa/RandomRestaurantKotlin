package com.chrisjanusa.randomizer.restaurant_favorite.actions

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.restaurant_favorite.updaters.FavoriteUpdater
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.channels.Channel


class FavoriteClickAction(private val restaurant: Restaurant, private val preferences: SharedPreferences?) :
    BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        updateChannel.send(FavoriteUpdater(restaurant, preferences))
    }
}