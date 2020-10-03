package com.chrisjanusa.randomizer.filter_distance

import androidx.fragment.app.Fragment
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle
import kotlinx.android.synthetic.main.filters.*

object DistanceUIManager : FeatureUIManager {
    private const val defaultDistanceTitle = "Max Distance"

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: BaseRestaurantFragment) {
        fragment.run {
            distance.setOnClickListener { clickSelectionFilter(Filter.Distance, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {
        fragment.run {
            val selected = !isDefault(state.maxMilesSelected)
            distance.text = if (selected) distanceToDisplayString(state.maxMilesSelected) else defaultDistanceTitle
            context?.let { renderFilterWithIconStyle(distance, selected, it) }
        }
    }
}