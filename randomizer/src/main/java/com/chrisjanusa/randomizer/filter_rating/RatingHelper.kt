package com.chrisjanusa.randomizer.filter_rating

const val defaultRating = 0f

fun ratingToDisplayString(distance: Float) = "Min %.1f Stars".format(distance)

fun isDefault(rating: Float) = rating == defaultRating