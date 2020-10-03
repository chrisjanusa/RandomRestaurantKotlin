package com.chrisjanusa.randomizer.filter_rating.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState

class SelectedRatingUpdater(private val newMinRating: Float) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(minRating = newMinRating, restaurantCacheValid = false, restaurantsSeenRecently = HashSet())
    }
}