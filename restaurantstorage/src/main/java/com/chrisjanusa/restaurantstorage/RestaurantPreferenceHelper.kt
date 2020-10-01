package com.chrisjanusa.restaurantstorage

import android.content.SharedPreferences
import com.chrisjanusa.yelp.models.Restaurant
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.util.*
import kotlin.collections.HashSet

object RestaurantPreferenceHelper {
    private val mapper = jacksonObjectMapper()

    fun retrieveCache(preferences: SharedPreferences?, preferenceKey: String): HashSet<Restaurant> {
        val json = preferences?.getString(preferenceKey, "")
        if (json.isNullOrEmpty()) {
            return HashSet()
        }
        return mapper.readValue(json)
    }

    fun retrieveListCache(preferences: SharedPreferences?, preferenceKey: String): List<Restaurant> {
        val json = preferences?.getString(preferenceKey, "")
        if (json.isNullOrEmpty()) {
            return LinkedList()
        }
        return mapper.readValue(json)
    }

    fun saveCache(preferences: SharedPreferences?, preferenceKey: String, cachedRestaurants: Set<Restaurant>) : Boolean {
        preferences?.let {
            with(it.edit()) {
                putString(preferenceKey, mapper.writeValueAsString(cachedRestaurants))
                return commit()
            }
        }
        return false
    }

    fun saveListCache(preferences: SharedPreferences?, preferenceKey: String, cachedRestaurants: List<Restaurant>) : Boolean {
        preferences?.let {
            with(it.edit()) {
                putString(preferenceKey, mapper.writeValueAsString(cachedRestaurants))
                return commit()
            }
        }
        return false
    }
}