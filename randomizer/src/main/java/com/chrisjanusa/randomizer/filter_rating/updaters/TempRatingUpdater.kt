package com.chrisjanusa.randomizer.filter_rating.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState

class TempRatingUpdater(private val newMinRating: Float) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(tempMinRating = newMinRating)
    }
}