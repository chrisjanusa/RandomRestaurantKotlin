package com.chrisjanusa.randomizer.filter_rating

import kotlin.math.roundToInt

const val defaultRating = 0f

fun ratingToDisplayString(distance: Float) : String {
    val intDistance = distance.roundToInt()
    return if (intDistance.toFloat() == distance) {
        "Min $intDistance Stars"
    } else {
        "Min %.1f Stars".format(distance)
    }
}

fun isDefault(rating: Float) = rating == defaultRating