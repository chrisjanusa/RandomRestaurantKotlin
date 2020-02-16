package com.chrisjanusa.randomizer.base.init

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_cuisine.CuisineHelper.Cuisine
import com.chrisjanusa.randomizer.filter_diet.DietHelper
import com.chrisjanusa.randomizer.filter_price.PriceHelper.Price

class InitUpdater(
    private val gpsOn: Boolean,
    private val openNowSelected: Boolean,
    private val favoriteOnlySelected: Boolean,
    private val maxMilesSelected: Float,
    private val diet: DietHelper.Diet,
    private val priceSet: HashSet<Price>,
    private val cuisineSet: HashSet<Cuisine>,
    private val currLat: Double,
    private val currLng: Double,
    private val locationText: String
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(
            gpsOn = gpsOn,
            priceSet = priceSet,
            openNowSelected = openNowSelected,
            favoriteOnlySelected = favoriteOnlySelected,
            maxMilesSelected = maxMilesSelected,
            diet = diet,
            cuisineSet = cuisineSet,
            currLat = currLat,
            currLng = currLng,
            locationText = locationText
        )
    }
}