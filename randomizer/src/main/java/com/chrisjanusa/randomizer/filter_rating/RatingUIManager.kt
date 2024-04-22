package com.chrisjanusa.randomizer.filter_rating

import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle

object RatingUIManager : FeatureUIManager {
    private const val defaultRatingTitle = "Rating"

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val filterBinding = baseFragmentDetails.getFiltersBinding()
                filterBinding.ratingButton.setOnClickListener { clickSelectionFilter(Filter.Rating, randomizerViewModel) }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val filterBinding = baseFragmentDetails.getFiltersBinding()
                val selected = !isDefault(state.minRating)
                filterBinding.ratingButton.text = if (selected) ratingToDisplayString(state.minRating) else defaultRatingTitle
                context?.let { renderFilterWithIconStyle(filterBinding.ratingButton, selected, it) }
            }
        }
    }
}