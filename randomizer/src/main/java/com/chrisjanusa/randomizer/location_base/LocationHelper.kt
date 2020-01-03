package com.chrisjanusa.randomizer.location_base

import android.location.Location

object LocationHelper {
    const val defaultLocationText = "Unknown"
    const val calculatingLocationText = "Locating"

    const val defaultLat = 200.0
    const val defaultLng = 200.0
    private const val spaceNeedleLat = 47.620422
    private const val spaceNeedleLng = -122.349358

    val defaultLocation : Location
        get() {
            val location = Location("")
            location.latitude = defaultLat
            location.longitude = defaultLng
            return  location
        }


    val defaultMapLocation : Location
        get() {
            val location = Location("")
            location.latitude = spaceNeedleLat
            location.longitude = spaceNeedleLng
            return location
        }

    fun isDefault(location: Location) : Boolean =
        defaultLat == location.latitude && defaultLng == location.longitude
}