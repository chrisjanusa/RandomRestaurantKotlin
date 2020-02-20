package com.chrisjanusa.randomizer.location_base

import android.location.Location

object LocationHelper {
    const val defaultLocationText = "Unknown"
    const val calculatingLocationText = "Locating"

    const val defaultLat = 200.0
    const val defaultLng = 200.0
    const val spaceNeedleLat = 47.620422
    const val spaceNeedleLng = -122.349358
    private const val minimumDistanceChange = 160.934f

    const val zoomLevel = 16f

    fun hasLocationChanged(prevLat: Double?, prevLng: Double?, currLat: Double, currLng: Double) : Boolean {
        return if (prevLat != null && prevLng != null) {
            val distance = FloatArray(3)
            Location.distanceBetween(prevLat, prevLng, currLat, currLng, distance)
            distance[0] > minimumDistanceChange
        } else {
            println("Updating to Null")
            true
        }
    }
}