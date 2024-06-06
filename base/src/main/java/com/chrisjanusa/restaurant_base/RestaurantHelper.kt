package com.chrisjanusa.restaurant_base

import com.chrisjanusa.restaurant.Category
import com.chrisjanusa.restaurant.Restaurant

fun categoriesToDisplayString(categories: List<Category>) = categories.joinToString { it.name.removeSuffix(" Restaurant") }

fun restaurantToPriceDistanceString(restaurant: Restaurant, showDistance: Boolean): String {
    val stringComponents = mutableListOf<String>()
    restaurant.price?.let { stringComponents.add(it) }
    if (showDistance) {
        restaurant.distance?.let {
            stringComponents.add(
                String.Companion.format(
                    "%.2f mi away",
                    metersToMiles(it)
                )
            )
        }
    }
    return stringComponents.joinToString(separator = " | ")
}

fun metersToMiles(distance: Int): Double = distance * 0.000621371