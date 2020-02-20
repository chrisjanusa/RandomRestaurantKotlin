package com.chrisjanusa.randomizer.location_base.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.LocationHelper.hasLocationChanged

class LocationUpdater(private val locationStr: String, private val currLat: Double, private val currLng: Double) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return if (hasLocationChanged(prevState.currLat, prevState.currLng, currLat, currLng)) {
            prevState.copy(
                locationText = locationStr,
                currLat = currLat,
                currLng = currLng,
                restaurantCacheValid = false
            )

        } else {
            prevState.copy(locationText = locationStr, currLat = currLat, currLng = currLng)
        }
    }
}