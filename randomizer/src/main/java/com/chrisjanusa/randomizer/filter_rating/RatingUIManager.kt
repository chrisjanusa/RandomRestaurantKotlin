package com.chrisjanusa.randomizer.filter_rating

import androidx.fragment.app.Fragment
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle

object RatingUIManager : FeatureUIManager {
    private const val defaultRatingTitle = "Rating"


    override fun init(randomizerViewModel: RandomizerViewModel, fragment: BaseRestaurantFragment) {
        fragment.run {
            // TODO: Synthetics
//            val binding = RandomizerFragmentBinding.inflate(layoutInflater
//            rating_button.setOnClickListener { clickSelectionFilter(Filter.Rating, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {
        fragment.run {
            val selected = !isDefault(state.minRating)
//            rating_button.text = if (selected) ratingToDisplayString(state.minRating) else defaultRatingTitle
//            context?.let { renderFilterWithIconStyle(rating_button, selected, it) }
        }
    }
}