package com.chrisjanusa.randomizer.filter_diet

import androidx.fragment.app.Fragment
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle
import com.chrisjanusa.randomizer.filter_diet.DietHelper.isDefault
import kotlinx.android.synthetic.main.filters.*

object DietUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: Fragment) {
        fragment.run {
            diet.setOnClickListener { clickSelectionFilter(Filter.Diet, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {
        fragment.run {
            val selected = !isDefault(state.diet)
            diet.text = state.diet.display
            context?.let { renderFilterWithIconStyle(diet, selected, it) }
        }
    }
}