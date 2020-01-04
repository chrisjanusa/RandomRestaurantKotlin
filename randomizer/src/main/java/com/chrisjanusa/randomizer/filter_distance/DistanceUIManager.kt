package com.chrisjanusa.randomizer.filter_distance

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.defaultDistanceTitle
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.distanceToDisplayString
import com.chrisjanusa.randomizer.filter_distance.DistanceHelper.isDefault
import kotlinx.android.synthetic.main.filters.*

object DistanceUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            distance.setOnClickListener { clickSelectionFilter(Filter.Distance, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            val selected = !isDefault(state.maxMilesSelected)
            distance.text = if (selected) distanceToDisplayString(state.maxMilesSelected) else defaultDistanceTitle
            context?.let { renderFilterStyle(distance, selected, it) }
        }
    }
}