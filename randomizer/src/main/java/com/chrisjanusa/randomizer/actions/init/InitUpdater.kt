package com.chrisjanusa.randomizer.actions.init

import com.chrisjanusa.randomizer.helpers.CategoryHelper.Category
import com.chrisjanusa.randomizer.helpers.RestrictionHelper
import com.chrisjanusa.base_randomizer.RandomizerState

class InitUpdater(
    private val gpsOn: Boolean,
    private val openNowSelected: Boolean,
    private val favoriteOnlySelected: Boolean,
    private val maxMilesSelected: Float,
    private val restriction: RestrictionHelper.Restriction,
    private val priceText: String,
    private val categoryString: String,
    private val categorySet: HashSet<Category>
) : com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(
            gpsOn = gpsOn,
            priceText = priceText,
            openNowSelected = openNowSelected,
            favoriteOnlySelected = favoriteOnlySelected,
            maxMilesSelected = maxMilesSelected,
            restriction = restriction,
            categoryString = categoryString,
            categorySet = categorySet
        )
    }
}