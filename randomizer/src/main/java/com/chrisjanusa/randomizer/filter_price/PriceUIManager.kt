package com.chrisjanusa.randomizer.filter_price

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.Filter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle
import com.chrisjanusa.randomizer.filter_price.PriceHelper.isDefault
import kotlinx.android.synthetic.main.filters.*

object PriceUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            price.setOnClickListener { clickSelectionFilter(Filter.Price, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            val selected = isDefault(state.priceText)
            price.text = state.priceText
            context?.let { renderFilterWithIconStyle(price, selected, it) }
        }
    }
}