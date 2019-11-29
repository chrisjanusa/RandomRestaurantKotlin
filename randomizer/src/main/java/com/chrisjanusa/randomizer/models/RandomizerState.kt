package com.chrisjanusa.randomizer.models

import android.location.Location

data class RandomizerState (
    val gpsOn: Boolean = true,
    val locationText: String = "Test",
    val location: Location? = null
)