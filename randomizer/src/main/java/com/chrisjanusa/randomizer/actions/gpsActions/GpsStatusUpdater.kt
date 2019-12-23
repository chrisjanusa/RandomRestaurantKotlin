package com.chrisjanusa.randomizer.actions.gpsActions

import com.chrisjanusa.base_randomizer.RandomizerState

class GpsStatusUpdater(private val gpsOn : Boolean, private val locationText : String) :
    com.chrisjanusa.base_randomizer.BaseUpdater {

    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(gpsOn = gpsOn, locationText = locationText)
    }
}