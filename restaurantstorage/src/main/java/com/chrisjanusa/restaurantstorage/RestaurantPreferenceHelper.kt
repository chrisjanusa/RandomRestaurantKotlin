package com.chrisjanusa.restaurantstorage

import android.content.SharedPreferences
import com.chrisjanusa.restaurant.Restaurant
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.util.*


fun retrieveListCache(preferences: SharedPreferences?, preferenceKey: String): List<Restaurant> {
    val mapper = jacksonObjectMapper()
    val json = preferences?.getString(preferenceKey, "")
    if (json.isNullOrEmpty()) {
        return LinkedList()
    }
    return mapper.readValue(json)
}

fun saveListCache(
    preferences: SharedPreferences?,
    preferenceKey: String,
    cachedRestaurants: List<Restaurant>
): Boolean {
    if (cachedRestaurants.isNotEmpty()) {
        val mapper = jacksonObjectMapper()
        preferences?.let {
            with(it.edit()) {
                putString(preferenceKey, mapper.writeValueAsString(cachedRestaurants))
                return commit()
            }
        }
    }
    return false
}

fun retrieveMapCache(preferences: SharedPreferences?, preferenceKey: String): Map<String, String> {
    val mapper = jacksonObjectMapper()
    val json = preferences?.getString(preferenceKey, "")
    if (json.isNullOrEmpty()) {
        return HashMap()
    }
    return mapper.readValue(json)
}

fun saveMapCache(
    preferences: SharedPreferences?,
    preferenceKey: String,
    cachedRestaurants: Map<String, String>
): Boolean {
    if (cachedRestaurants.isNotEmpty()) {
        val mapper = jacksonObjectMapper()
        preferences?.let {
            with(it.edit()) {
                putString(preferenceKey, mapper.writeValueAsString(cachedRestaurants))
                return commit()
            }
        }
    }
    return false
}
