package com.chrisjanusa.randomizer.base.init

import android.location.Location
import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper
import com.chrisjanusa.randomizer.base.models.RandomizerState

class InitUpdater(
    private val gpsOn: Boolean,
    private val openNowSelected: Boolean,
    private val favoriteOnlySelected: Boolean,
    private val maxMilesSelected: Float,
    private val restriction: RestrictionHelper.Restriction,
    private val priceText: String,
    private val categoryString: String,
    private val categorySet: HashSet<Category>,
    private val location: Location,
    private val locationText: String
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(
            gpsOn = gpsOn,
            priceText = priceText,
            openNowSelected = openNowSelected,
            favoriteOnlySelected = favoriteOnlySelected,
            maxMilesSelected = maxMilesSelected,
            restriction = restriction,
            categoryString = categoryString,
            categorySet = categorySet,
            location = location,
            locationText = locationText
        )
    }
}