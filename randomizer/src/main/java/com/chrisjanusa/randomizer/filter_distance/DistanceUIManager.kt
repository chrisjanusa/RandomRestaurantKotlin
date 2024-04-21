package com.chrisjanusa.randomizer.filter_distance

import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle

object DistanceUIManager : FeatureUIManager {
    private const val defaultDistanceTitle = "Max Distance"

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val filterBinding = baseFragmentDetails.getFiltersBinding()
                filterBinding.distance.setOnClickListener { clickSelectionFilter(Filter.Distance, randomizerViewModel) }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val filterBinding = baseFragmentDetails.getFiltersBinding()
                val selected = !isDefault(state.maxMilesSelected)
                filterBinding.distance.text = if (selected) distanceToDisplayString(state.maxMilesSelected) else defaultDistanceTitle
                context?.let { renderFilterWithIconStyle(filterBinding.distance, selected, it) }
            }
        }
    }
}