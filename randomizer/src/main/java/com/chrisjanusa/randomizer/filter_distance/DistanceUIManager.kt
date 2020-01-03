package com.chrisjanusa.randomizer.filter_distance

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import kotlinx.android.synthetic.main.filters.*

object DistanceUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            distance.setOnClickListener { clickSelectionFilter(Filter.Distance, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            val selected = !DistanceHelper.isDefault(state.maxMilesSelected)
            distance.text = if (selected) DistanceHelper.distanceToDisplayString(state.maxMilesSelected) else DistanceHelper.defaultDistanceTitle
            context?.let { renderFilterStyle(distance, selected, it) }
        }
    }
}