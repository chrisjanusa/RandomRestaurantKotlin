package com.chrisjanusa.randomizer.base.init

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.MapUpdate
import com.chrisjanusa.randomizer.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.setFromSaveString
import com.chrisjanusa.randomizer.location_gps.GpsHelper.requestLocation
import com.chrisjanusa.randomizer.location_search.updaters.LastManualLocationUpdater
import com.chrisjanusa.randomizer.location_base.LocationHelper
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLocation
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultLocationText
import com.chrisjanusa.randomizer.location_base.LocationHelper.defaultMapLocation
import com.chrisjanusa.randomizer.location_base.LocationHelper.isDefault
import com.chrisjanusa.randomizer.location_base.LocationHelper.latLang
import com.chrisjanusa.randomizer.location_base.updaters.LocationTextUpdater
import kotlinx.coroutines.channels.Channel

class InitAction(private val activity: Activity?) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapUpdate>
    ) {
        val preferenceData =
            PreferenceHelper.retrieveState(activity?.getPreferences(Context.MODE_PRIVATE))

        preferenceData?.run {
            val restrictionObject = RestrictionHelper.restrictionFromIdentifier(restriction)
            val categorySet = setFromSaveString(categoryString)
            val location = Location("")
            location.longitude = curr_lng
            location.latitude = curr_lat

            if (isDefault(location)) {
                updateChannel.send(
                    InitUpdater(
                        gpsOn,
                        openNowSelected,
                        favoriteOnlySelected,
                        maxMilesSelected,
                        restrictionObject,
                        priceSelected,
                        categoryString,
                        categorySet,
                        defaultLocation,
                        defaultLocationText
                    )
                )
                mapChannel.send(MapUpdate(defaultMapLocation.latLang(),false))
            } else {
                val locationName = activity?.let { activity ->
                    Geocoder(activity).getFromLocation(location.latitude, location.longitude, 1)
                        .getOrNull(0)?.locality
                } ?: "Unknown"

                updateChannel.send(
                    InitUpdater(
                        gpsOn,
                        openNowSelected,
                        favoriteOnlySelected,
                        maxMilesSelected,
                        restrictionObject,
                        priceSelected,
                        categoryString,
                        categorySet,
                        location,
                        locationName
                    )
                )
                mapChannel.send(MapUpdate(location.latLang(), false))
                if (!gpsOn) {
                    updateChannel.send(LastManualLocationUpdater(locationName, location))
                }
            }

            if (gpsOn) {
                updateChannel.send(LocationTextUpdater(LocationHelper.calculatingLocationText))
                activity?.let { requestLocation(it, updateChannel, eventChannel, mapChannel) }
            }
        }
    }

}