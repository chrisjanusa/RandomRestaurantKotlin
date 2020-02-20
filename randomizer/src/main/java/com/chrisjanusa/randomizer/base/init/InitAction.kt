package com.chrisjanusa.randomizer.base.init

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.cuisineFromIdentifierString
import com.chrisjanusa.randomizer.filter_diet.DietHelper.dietFromIdentifier
import com.chrisjanusa.randomizer.filter_price.PriceHelper.priceFromSaveString
import com.chrisjanusa.randomizer.location_base.LocationHelper
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLocationText
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
        val preferenceData = PreferenceHelper.retrieveState(activity?.getPreferences(Context.MODE_PRIVATE))

        preferenceData?.run {
            val dietObject = dietFromIdentifier(diet)
            val priceSet = priceFromSaveString(priceSelected)
            val cuisineSet = cuisineFromIdentifierString(cuisineString)
            val locationName = if (currLat == null || currLng == null) {
                defaultLocationText
            } else {
                getTextFromLatLng(currLat, currLng)
            }

            updateChannel.send(
                InitUpdater(
                    gpsOn,
                    openNowSelected,
                    favoriteOnlySelected,
                    maxMilesSelected,
                    dietObject,
                    priceSet,
                    cuisineSet,
                    currLat,
                    currLng,
                    locationName,
                    currRestaurant,
                    restaurants,
                    cacheValidity
                )
            )

            if (gpsOn) {
                updateChannel.send(LocationTextUpdater(LocationHelper.calculatingLocationText))
                activity?.let {
                    requestLocation(it, updateChannel, eventChannel, mapChannel, currLat, currLng)
                }
            } else {
                updateChannel.send(LastManualLocationUpdater(locationName))
            }
        }
    }

    private fun getTextFromLatLng(currLat: Double, currLng: Double): String {
        return activity?.let {
            Geocoder(it)
                .getFromLocation(currLat, currLng, 1)
                .getOrNull(0)
                ?.locality
        } ?: defaultLocationText
    }

}
