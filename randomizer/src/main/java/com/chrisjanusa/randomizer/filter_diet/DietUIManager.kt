package com.chrisjanusa.randomizer.filter_diet

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle
import com.chrisjanusa.randomizer.filter_diet.DietHelper.isDefault
import kotlinx.android.synthetic.main.filters.*

object DietUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            diet.setOnClickListener { clickSelectionFilter(Filter.Diet, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            val selected = !isDefault(state.diet)
            diet.text = state.diet.display
            context?.let { renderFilterWithIconStyle(diet, selected, it) }
        }
    }
}