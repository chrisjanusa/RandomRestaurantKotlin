package com.chrisjanusa.randomizer.location_base

import android.app.Activity
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.defaultLocationText
import com.chrisjanusa.yelp.models.Restaurant
import kotlinx.coroutines.channels.Channel

const val calculatingLocationText = "Locating"
const val spaceNeedleLat = 47.620422
const val spaceNeedleLng = -122.349358
private const val minimumDistanceChange = 160.934f

const val zoomLevel = 16f
const val cameraSpeed = 700

fun hasLocationChanged(
    prevLat: Double?,
    prevLng: Double?,
    currLat: Double,
    currLng: Double
): Boolean {
    return if (prevLat != null && prevLng != null) {
        val distance = FloatArray(3)
        Location.distanceBetween(prevLat, prevLng, currLat, currLng, distance)
        distance[0] > minimumDistanceChange
    } else {
        true
    }
}

suspend fun initMapEvent(
    mapChannel: Channel<MapEvent>,
    currRestaurant: Restaurant?,
    currLat: Double?,
    currLng: Double?
) {
    if (currRestaurant != null) {
        mapChannel.send(
            MapEvent(
                currRestaurant.coordinates.latitude,
                currRestaurant.coordinates.longitude,
                true
            )
        )
    } else if (currLat == null || currLng == null) {
        mapChannel.send(MapEvent(spaceNeedleLat, spaceNeedleLng, false))
    } else {
        mapChannel.send(MapEvent(currLat, currLng, false))
    }
}

fun getTextFromLatLng(activity: Activity, currLat: Double, currLng: Double): String {
    return try {
        Geocoder(activity)
            .getFromLocation(currLat, currLng, 1)
            .getOrNull(0)
            ?.locality
            ?: defaultLocationText
    } catch (throwable : Throwable) {
        throwable.message?.let { Log.e("Random Restaurant Error", it) }
        defaultLocationText
    }
}
