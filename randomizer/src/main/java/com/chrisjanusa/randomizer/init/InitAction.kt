package com.chrisjanusa.randomizer.init

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapUpdate
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.defaultLocationText
import com.chrisjanusa.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.database_transition.transitionDatabase
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.cuisineFromIdentifierString
import com.chrisjanusa.randomizer.filter_diet.DietHelper.dietFromIdentifier
import com.chrisjanusa.randomizer.filter_price.PriceHelper.priceFromSaveString
import com.chrisjanusa.randomizer.location_base.LocationHelper
import com.chrisjanusa.randomizer.location_base.LocationHelper.getTextFromLatLng
import com.chrisjanusa.randomizer.location_base.LocationHelper.initMapUpdate
import com.chrisjanusa.randomizer.location_base.LocationHelper.spaceNeedleLat
import com.chrisjanusa.randomizer.location_base.LocationHelper.spaceNeedleLng
import com.chrisjanusa.randomizer.location_base.updaters.LocationTextUpdater
import com.chrisjanusa.randomizer.location_gps.GpsHelper.requestLocation
import com.chrisjanusa.randomizer.location_search.updaters.LastManualLocationUpdater
import kotlinx.coroutines.channels.Channel

class InitAction(private val activity: Activity?) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val preferenceData = PreferenceHelper.retrieveState(preferences)

        preferenceData?.run {
            val dietObject = dietFromIdentifier(diet)
            val priceSet = priceFromSaveString(priceSelected)
            val cuisineSet = cuisineFromIdentifierString(cuisineString)
            val locationName = if (currLat == null || currLng == null) {
                defaultLocationText
            } else {
                activity?.let { getTextFromLatLng(activity, currLat!!, currLng!!) } ?: defaultLocationText
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
                    favSet,
                    blockSet,
                    history
                )
            )

            initMapUpdate(mapChannel, null, currLat, currLng)

            if (gpsOn) {
                updateChannel.send(LocationTextUpdater(LocationHelper.calculatingLocationText))
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
