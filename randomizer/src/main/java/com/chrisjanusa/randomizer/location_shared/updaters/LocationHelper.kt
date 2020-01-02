package com.chrisjanusa.randomizer.location_shared.updaters

import android.location.Location

object LocationHelper {
    const val defaultLat = 200.0
    const val defaultLng = 200.0
    val defaultLocation : Location
        get() = run {
            val location = Location("")
            location.latitude = defaultLat
            location.longitude = defaultLng
            location
        }

    private const val spaceNeedleLat = 47.620422
    private const val spaceNeedleLng = -122.349358
    val defaultMapLocation : Location
        get() = run {
            val location = Location("")
            location.latitude = spaceNeedleLat
            location.longitude = spaceNeedleLng
            location
        }

    const val defaultLocationText = "Unknown"
    const val calculatingLocationText = "Locating"

    fun isDefault(location: Location) : Boolean =
        defaultLat == location.latitude && defaultLng == location.longitude
}