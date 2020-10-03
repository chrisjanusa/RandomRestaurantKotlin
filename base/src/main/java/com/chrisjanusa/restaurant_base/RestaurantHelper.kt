package com.chrisjanusa.restaurant_base

import com.chrisjanusa.yelp.models.Category
import com.chrisjanusa.yelp.models.Restaurant

fun categoriesToDisplayString(categories: List<Category>): String {
    val displayString = StringBuilder()
    for (category in categories) {
        displayString.append(category.title)
        displayString.append(", ")
    }
    return displayString.dropLast(2).toString()
}

fun restaurantToPriceDistanceString(restaurant: Restaurant, showDistance: Boolean): String {
    val delimiter = " | "
    val priceDistanceString = StringBuilder()
    restaurant.price?.let { priceDistanceString.append("$it$delimiter") }
    if (showDistance) {
        restaurant.distance.let {
            priceDistanceString.append(
                String.Companion.format(
                    "%.2f mi away$delimiter",
                    metersToMiles(it)
                )
            )
        }
    }
    return priceDistanceString.dropLast(3).toString()
}

fun metersToMiles(distance: Double): Double = distance * 0.000621371