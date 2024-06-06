package com.chrisjanusa.randomizer.database_transition

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.preferences.PreferenceHelper
import com.chrisjanusa.foursquare.FourSquareRepository.getBusinessSearchResults
import com.chrisjanusa.randomizer.database_transition.updaters.AddBlockUpdater
import com.chrisjanusa.randomizer.database_transition.updaters.AddFavoriteUpdater
import com.chrisjanusa.restaurant.toDomainModel
import com.chrisjanusa.restaurantstorage.retrieveYelpListCache
import com.chrisjanusa.restaurantstorage.saveYelpListCache
import com.chrisjanusa.yelp.models.YelpRestaurant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

private const val databaseTransitioned = "transitionedToFourSquare"

suspend fun transitionDatabase(preferences: SharedPreferences?, updateChannel: Channel<BaseUpdater>) {
    val alreadyInit = preferences?.getBoolean(databaseTransitioned, false) ?: true
    if (!alreadyInit) {
        var transitionedAll = true
        var request = GlobalScope.launch {
            val remainingRestaurants = mutableListOf<YelpRestaurant>()
            for (res in retrieveYelpListCache(preferences, PreferenceHelper.StateObject.YelpFavRestaurants.key)) {
                launch {
                    try {
                        val businesses =
                            getBusinessSearchResults(
                                latitude = res.coordinates.latitude,
                                longitude = res.coordinates.longitude,
                                term = res.name,
                                limit = 1,
                                openNow = false
                            ).body()?.results ?: emptyList()
                        if (businesses.isNotEmpty()) {
                            updateChannel.send(
                                AddFavoriteUpdater(
                                    businesses[0].toDomainModel()
                                )
                            )
                        }
                    } catch (exception: Exception) {
                        transitionedAll = false
                        remainingRestaurants.add(res)
                        Log.d("caught exception", exception.toString())
                    }
                }
            }
            saveYelpListCache(preferences, PreferenceHelper.StateObject.YelpFavRestaurants.key, remainingRestaurants)
        }

        request.join()
        request = GlobalScope.launch {
            val remainingRestaurants = mutableListOf<YelpRestaurant>()
            for (res in retrieveYelpListCache(preferences, PreferenceHelper.StateObject.YelpBlockedRestaurants.key)) {
                launch {
                    try {
                        val businesses =
                            getBusinessSearchResults(
                                latitude = res.coordinates.latitude,
                                longitude = res.coordinates.longitude,
                                term = res.name,
                                limit = 1,
                                openNow = false
                            ).body()?.results ?: emptyList()
                        if (businesses.isNotEmpty()) {
                            updateChannel.send(AddBlockUpdater(businesses[0].toDomainModel()))
                        }
                    } catch (exception: Exception) {
                        transitionedAll = false
                        remainingRestaurants.add(res)
                        Log.d("caught exception", exception.toString())
                    }
                }
            }
            saveYelpListCache(preferences, PreferenceHelper.StateObject.YelpBlockedRestaurants.key, remainingRestaurants)
        }
        request.join()
        if (transitionedAll) {
            preferences?.let {
                with(it.edit()) {
                    putBoolean(databaseTransitioned, true)
                    apply()
                }
            }
        }
    }
}
