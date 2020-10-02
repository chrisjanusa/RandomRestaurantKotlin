package com.chrisjanusa.randomizer.filter_price

import androidx.fragment.app.Fragment
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.base.models.defaultPriceTitle
import com.chrisjanusa.base.models.delimiter
import com.chrisjanusa.base.models.enums.Filter
import com.chrisjanusa.base.models.enums.Price
import com.chrisjanusa.randomizer.filter_base.FilterHelper.clickSelectionFilter
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterWithIconStyle
import kotlinx.android.synthetic.main.filters.*

object PriceUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: Fragment) {
        fragment.run {
            price.setOnClickListener { clickSelectionFilter(Filter.Price, randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {
        fragment.run {
            val selected = state.priceSet.isNotEmpty()
            price.text = state.priceSet.toDisplayString()
            context?.let { renderFilterWithIconStyle(price, selected, it) }
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