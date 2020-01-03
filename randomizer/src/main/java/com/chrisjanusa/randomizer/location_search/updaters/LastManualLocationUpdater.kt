package com.chrisjanusa.randomizer.location_search.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState

class LastManualLocationUpdater(
    private val locationStr: String,
    private val lastManualLat: Double,
    private val lastManualLng: Double
) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(
            lastManualLocationText = locationStr,
            lastManualLat = lastManualLat,
            lastManualLng = lastManualLng
        )
    }
}