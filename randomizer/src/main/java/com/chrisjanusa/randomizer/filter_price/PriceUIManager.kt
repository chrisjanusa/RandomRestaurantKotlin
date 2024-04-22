package com.chrisjanusa.randomizer.filter_price

import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.defaultPriceTitle
import com.chrisjanusa.base.models.delimiter
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.base.models.enums.Price
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle

object PriceUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val filterBinding = baseFragmentDetails.getFiltersBinding()
                filterBinding.price.setOnClickListener { clickSelectionFilter(Filter.Price, randomizerViewModel) }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val filterBinding = baseFragmentDetails.getFiltersBinding()
                val selected = state.priceSet.isNotEmpty()
                filterBinding.price.text = state.priceSet.toDisplayString()
                context?.let { renderFilterWithIconStyle(filterBinding.price, selected, it) }
            }
        }
    }

    fun HashSet<Price>.toDisplayString(): String {
        if (isEmpty()) {
            return defaultPriceTitle
        }
        val builder = StringBuilder()
        val priceList = listOf(Price.One, Price.Two, Price.Three, Price.Four)
        priceList.forEach { if (contains(it)) builder.append(it.text + delimiter) }
        return builder.dropLast(2).toString()
    }
}