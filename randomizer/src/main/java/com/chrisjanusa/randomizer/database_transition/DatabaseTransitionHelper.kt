package com.chrisjanusa.randomizer.database_transition

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.database_transition.database.BlockDBHelper
import com.chrisjanusa.randomizer.database_transition.database.FavoritesDBHelper
import com.chrisjanusa.randomizer.database_transition.updaters.AddBlockUpdater
import com.chrisjanusa.randomizer.database_transition.updaters.AddFavoriteUpdater
import com.chrisjanusa.yelp.YelpRepository.getBusinessSearchResults
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

private const val databaseTransitioned = "databaseTransitioned"

suspend fun transitionDatabase(preferences: SharedPreferences?, context: Context, updateChannel: Channel<BaseUpdater>) {
    val alreadyInit = preferences?.getBoolean(databaseTransitioned, false) ?: true
    if (!alreadyInit) {
        var transitionedAll = true
        var request = GlobalScope.launch {
            val database = FavoritesDBHelper(context)
            for (res in database.getFavoriteRestaurants()) {
                launch {
                    try {
                        val businesses =
                            getBusinessSearchResults(
                                res.lat,
                                res.lon,
                                res.name,
                                limit = 1,
                                open_now = false
                            ).businesses
                        if (businesses.isNotEmpty()) {
                            updateChannel.send(
                                AddFavoriteUpdater(
                                    businesses[0],
                                    preferences,
                                    context
                                )
                            )
                        }
                    } catch (exception: Exception) {
                        transitionedAll = false
                        Log.d("caught exception", exception.toString())
                    }
                }
            }
        }
        request.join()
        request = GlobalScope.launch {
            val database = BlockDBHelper(context)
            for (res in database.getBlockedRestaurants()) {
                launch {
                    try {
                        val businesses =
                            getBusinessSearchResults(
                                res.lat,
                                res.lon,
                                res.name,
                                limit = 1,
                                open_now = false
                            ).businesses
                        if (businesses.isNotEmpty()) {
                            updateChannel.send(AddBlockUpdater(businesses[0], preferences, context))
                        }
                    } catch (exception: Exception) {
                        transitionedAll = false
                        Log.d("caught exception", exception.toString())
                    }
                }
            }
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
