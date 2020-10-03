package com.chrisjanusa.randomizer.location_base.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_base.hasLocationChanged

class LocationUpdater(private val locationStr: String, private val currLat: Double, private val currLng: Double) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return if (hasLocationChanged(prevState.currLat, prevState.currLng, currLat, currLng)) {
            prevState.copy(
                locationText = locationStr,
                currLat = currLat,
                currLng = currLng,
                restaurantCacheValid = false,
                restaurantsSeenRecently = HashSet()
            )

        } else {
            prevState.copy(locationText = locationStr, currLat = currLat, currLng = currLng)
        }
    }
}