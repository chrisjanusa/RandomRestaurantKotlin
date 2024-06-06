package com.chrisjanusa.restaurantstorage

import android.content.SharedPreferences
import com.chrisjanusa.yelp.models.YelpRestaurant
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.util.*


fun retrieveYelpListCache(preferences: SharedPreferences?, preferenceKey: String): List<YelpRestaurant> {
    val mapper = jacksonObjectMapper()
    val json = preferences?.getString(preferenceKey, "")
    if (json.isNullOrEmpty()) {
        return LinkedList()
    }
    return mapper.readValue(json)
}

fun saveYelpListCache(
    preferences: SharedPreferences?,
    preferenceKey: String,
    cachedRestaurants: List<YelpRestaurant>
): Boolean {
    val mapper = jacksonObjectMapper()
    preferences?.let {
        with(it.edit()) {
            putString(preferenceKey, mapper.writeValueAsString(cachedRestaurants))
            return commit()
        }
    }
    return false
}
