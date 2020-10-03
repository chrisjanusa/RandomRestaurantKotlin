package com.chrisjanusa.randomizer.init

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.defaultLocationText
import com.chrisjanusa.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.database_transition.transitionDatabase
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.cuisineFromIdentifierString
import com.chrisjanusa.randomizer.filter_diet.dietFromIdentifier
import com.chrisjanusa.randomizer.filter_price.priceFromSaveString
import com.chrisjanusa.randomizer.location_base.calculatingLocationText
import com.chrisjanusa.randomizer.location_base.getTextFromLatLng
import com.chrisjanusa.randomizer.location_base.initMapEvent
import com.chrisjanusa.randomizer.location_base.updaters.LocationTextUpdater
import com.chrisjanusa.randomizer.location_gps.GpsHelper.requestLocation
import com.chrisjanusa.randomizer.location_search.updaters.LastManualLocationUpdater
import kotlinx.coroutines.channels.Channel

class InitAction(private val activity: FragmentActivity?) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        if (currentState.value?.stateInitialized == true) {
            activity?.let { activity ->
                currentState.value?.let { state ->
                    state.currRestaurant?.let { restaurant ->
                        eventChannel.send(InitCurrRestaurantEvent(restaurant, state, activity))
                    }
                }
            }
            return
        }
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val preferenceData = PreferenceHelper.retrieveState(preferences)

        preferenceData?.run {
            val dietObject = dietFromIdentifier(diet)
            val priceSet = priceFromSaveString(priceSelected)
            val cuisineSet = cuisineFromIdentifierString(cuisineString)
            val locationName = if (currLat == null || currLng == null) {
                defaultLocationText
            } else {
                activity?.let { getTextFromLatLng(activity, currLat!!, currLng!!) }
                    ?: defaultLocationText
            }

            updateChannel.send(
                InitUpdater(
                    gpsOn,
                    openNowSelected,
                    favoriteOnlySelected,
                    fastFoodSelected,
                    sitDownSelected,
                    maxMilesSelected,
                    dietObject,
                    priceSet,
                    cuisineSet,
                    currLat,
                    currLng,
                    locationName,
                    cacheValidity,
                    restaurantsSeenRecently,
                    favList,
                    blockList,
                    history
                )
            )

            initMapEvent(mapChannel, null, currLat, currLng)

            if (gpsOn) {
                updateChannel.send(LocationTextUpdater(calculatingLocationText))
                activity?.let {
                    requestLocation(it, updateChannel, eventChannel, mapChannel, currLat, currLng)
                }
            } else {
                updateChannel.send(LastManualLocationUpdater(locationName))
            }

            activity?.let {
                transitionDatabase(preferences, activity.applicationContext, updateChannel)
            }
        }
    }
}
