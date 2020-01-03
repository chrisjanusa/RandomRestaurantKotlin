package com.chrisjanusa.randomizer.filter_distance

import kotlin.math.roundToInt

object DistanceHelper {
    const val defaultDistanceTitle = "Max Distance"
    const val defaultDistance = 5.0f
    const val minDistance = 0.1f
    const val maxDistance = 20.0f
    private const val totalDistance = maxDistance - minDistance

    fun distanceToDisplayString(distance: Float) = "%.1f Miles".format(distance)

    fun isDefault(distance: Float) = (distance * 10).roundToInt() == (defaultDistance * 10).roundToInt()

    fun percentToDistance(percent: Float) = minDistance + (totalDistance * percent)

    fun distanceToPercent(distance: Float) = (distance - minDistance) / totalDistance
}