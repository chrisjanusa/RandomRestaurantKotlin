package com.chrisjanusa.randomizer.models

data class RandomizerState (
    val gpsOn: Boolean = true,
    val locationText: String = "Test",
    val askForPermission: Boolean = false,
    val askForLocationSetting: Boolean = false
)