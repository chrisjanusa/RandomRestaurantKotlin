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
            location.longitude = curr_lng.toDouble().takeUnless { curr_lng > 180 } ?: -122.349358
            location.latitude = curr_lat.toDouble().takeUnless { curr_lat > 180 } ?: 47.620422
            val locationName = activity?.let {
                val locationList = Geocoder(it).getFromLocation(location.latitude, location.longitude, 1)
                if (locationList.size >0){
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