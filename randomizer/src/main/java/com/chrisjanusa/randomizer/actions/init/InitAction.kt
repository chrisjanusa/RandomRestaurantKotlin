package com.chrisjanusa.randomizer.actions.init

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import com.chrisjanusa.randomizer.helpers.CategoryHelper
import com.chrisjanusa.randomizer.helpers.PreferenceHelper
import com.chrisjanusa.randomizer.helpers.RestrictionHelper
import com.chrisjanusa.base_randomizer.RandomizerState
import kotlinx.coroutines.channels.Channel

class InitAction(private val activity: Activity?) : com.chrisjanusa.base_randomizer.BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<com.chrisjanusa.base_randomizer.BaseUpdater>,
        eventChannel: Channel<com.chrisjanusa.base_randomizer.BaseEvent>
    ) {
        val preferenceData = PreferenceHelper.retrieveState(activity?.getPreferences(Context.MODE_PRIVATE))

        preferenceData?.run {
            val restrictionObject = RestrictionHelper.restrictionFromIdentifier(restriction)
            val categorySet = CategoryHelper.setFromSaveString(categoryString)
            updateChannel.send(
                InitUpdater(
                    gpsOn,
                    openNowSelected,
                    favoriteOnlySelected,
                    maxMilesSelected,
                    restrictionObject,
                    priceSelected,
                    categoryString,
                    categorySet
                )
            )
        }
    }

}