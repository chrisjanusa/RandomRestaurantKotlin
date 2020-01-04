package com.chrisjanusa.randomizer.filter_restriction

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper.defaultRestrictionTitle
import com.chrisjanusa.randomizer.filter_restriction.RestrictionHelper.isDefault
import kotlinx.android.synthetic.main.filters.*

object RestrictionUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            restrictions.setOnClickListener { clickSelectionFilter(Filter.Restriction, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            val selected = !isDefault(state.restriction)
            restrictions.text = if (selected) state.restriction.display else defaultRestrictionTitle
            context?.let { renderFilterStyle(restrictions, selected, it) }
        }
    }
}