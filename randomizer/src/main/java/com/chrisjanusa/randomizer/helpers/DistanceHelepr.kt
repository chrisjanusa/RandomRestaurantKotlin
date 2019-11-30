package com.chrisjanusa.randomizer.helpers

object DistanceHelepr {
    const val defaultDistanceTitle = "Max Distance"
    const val defaultDistance = 5.0f
    const val minDistance = 0.1f
    const val maxDistance = 20.0f
    const val total = minDistance + maxDistance

    fun distanceToDisplayString(distance : Float) : String {
        return if (distance != 5.0f) {
            "%.1f Miles".format(distance)
        }
        else {
            defaultDistanceTitle
        }
    }

    fun percentToDistance(percent : Float) : Float{
        return minDistance + (total  * percent)
    }

    fun distanceToPercent(distance : Float) : Float{
        return (distance - minDistance) / total
    }
}