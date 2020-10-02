package com.chrisjanusa.randomizer.filter_distance

import com.chrisjanusa.base.models.defaultDistance
import kotlin.math.roundToInt

object DistanceHelper {
    const val minDistance = 0.1f
    const val maxDistance = 20.0f
    private const val totalDistance = maxDistance - minDistance

    fun distanceToDisplayString(distance: Float) = "%.1f Miles".format(distance)

    fun isDefault(distance: Float) = (distance * 10).roundToInt() == (defaultDistance * 10).roundToInt()

    fun percentToDistance(percent: Float) : Float {
        val tempDistance = minDistance + (totalDistance * percent)
        return when {
            tempDistance > 10 -> tempDistance.roundToInt().toFloat()
            tempDistance > 5 -> (tempDistance * 2).roundToInt().toFloat() / 2
            else -> (tempDistance * 10).roundToInt().toFloat() / 10
        }
    }

    fun distanceToPercent(distance: Float) = (distance - minDistance) / totalDistance

    fun milesToMeters(distance: Float) = distance * 1609.34

    fun metersToMiles(distance: Double) : Double = distance * 0.000621371
}