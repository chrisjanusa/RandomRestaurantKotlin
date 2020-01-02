package com.chrisjanusa.randomizer.base.init

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.base.interfaces.BaseAction
import com.chrisjanusa.randomizer.base.interfaces.BaseEvent
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.preferences.PreferenceHelper
import com.chrisjanusa.randomizer.filter_category.CategoryHelper
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.defaultLocation
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.defaultLocationText
import com.chrisjanusa.randomizer.location_shared.updaters.LocationHelper.isDefault
import kotlinx.coroutines.channels.Channel

class InitAction(private val activity: Activity?) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>
    ) {
        val preferenceData =
            PreferenceHelper.retrieveState(activity?.getPreferences(Context.MODE_PRIVATE))

        preferenceData?.run {
            val restrictionObject = RestrictionHelper.restrictionFromIdentifier(restriction)
            val categorySet = CategoryHelper.setFromSaveString(categoryString)
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
            } else {

                val locationName = activity?.let {
                    val locationList = Geocoder(it).getFromLocation(location.latitude, location.longitude, 1)
                    if (locationList.size > 0) {
                        locationList[0].locality
                    } else {
                        null
                    }
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
            }
        }
    }

}