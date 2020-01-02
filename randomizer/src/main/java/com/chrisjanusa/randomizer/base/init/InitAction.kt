package com.chrisjanusa.randomizer.base.init

import android.app.Activity
import android.content.Context
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