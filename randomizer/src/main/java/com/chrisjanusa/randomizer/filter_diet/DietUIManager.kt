package com.chrisjanusa.randomizer.filter_diet

import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle

object DietUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                baseFragmentDetails.getFiltersBinding().diet.setOnClickListener { clickSelectionFilter(Filter.Diet, randomizerViewModel) }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val selected = !isDefault(state.diet)
                val dietBinding = baseFragmentDetails.getFiltersBinding().diet
                dietBinding.text = state.diet.display
                context?.let { renderFilterWithIconStyle(dietBinding, selected, it) }
            }
        }
    }
}