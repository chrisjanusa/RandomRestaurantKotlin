package com.chrisjanusa.randomizer.base.init

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.Cuisine
import com.chrisjanusa.randomizer.filter_diet.DietHelper

class InitUpdater(
    private val gpsOn: Boolean,
    private val openNowSelected: Boolean,
    private val favoriteOnlySelected: Boolean,
    private val maxMilesSelected: Float,
    private val diet: DietHelper.Diet,
    private val priceText: String,
    private val cuisineString: String,
    private val cuisineSet: HashSet<Cuisine>,
    private val currLat: Double,
    private val currLng: Double,
    private val locationText: String
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(
            gpsOn = gpsOn,
            priceText = priceText,
            openNowSelected = openNowSelected,
            favoriteOnlySelected = favoriteOnlySelected,
            maxMilesSelected = maxMilesSelected,
            diet = diet,
            cuisineString = cuisineString,
            cuisineSet = cuisineSet,
            currLat = currLat,
            currLng = currLng,
            locationText = locationText
        )
    }
}