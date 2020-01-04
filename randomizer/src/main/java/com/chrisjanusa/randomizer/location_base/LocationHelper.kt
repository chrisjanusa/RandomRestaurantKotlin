package com.chrisjanusa.randomizer.location_base

object LocationHelper {
    const val defaultLocationText = "Unknown"
    const val calculatingLocationText = "Locating"

    const val defaultLat = 200.0
    const val defaultLng = 200.0
    const val spaceNeedleLat = 47.620422
    const val spaceNeedleLng = -122.349358

    const val zoomLevel = 16f

    fun isDefault(lat: Double, lng: Double): Boolean = defaultLat == lat && defaultLng == lng
}