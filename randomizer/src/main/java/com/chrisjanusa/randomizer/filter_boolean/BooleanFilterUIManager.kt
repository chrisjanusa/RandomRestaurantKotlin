package com.chrisjanusa.randomizer.filter_boolean

import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.randomizer.filter_boolean.actions.FastFoodClickedAction
import com.chrisjanusa.randomizer.filter_boolean.actions.FavoriteClickedAction
import com.chrisjanusa.randomizer.filter_boolean.actions.OpenNowClickedAction
import com.chrisjanusa.randomizer.filter_boolean.actions.SitDownClickedAction

object BooleanFilterUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val filterBinding = baseFragmentDetails.getFiltersBinding()
                filterBinding.openNow.setOnClickListener { sendAction(OpenNowClickedAction(), randomizerViewModel) }
                filterBinding.favoritesOnly.setOnClickListener { sendAction(FavoriteClickedAction(), randomizerViewModel) }
                filterBinding.fastFood.setOnClickListener { sendAction(FastFoodClickedAction(), randomizerViewModel) }
//                filterBinding.sitDown.setOnClickListener { sendAction(SitDownClickedAction(), randomizerViewModel) }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.context?.let {
                val filterBinding = baseFragmentDetails.getFiltersBinding()
                renderFilterStyle(filterBinding.openNow, state.openNowSelected, it)
                renderFilterStyle(filterBinding.favoritesOnly, state.favoriteOnlySelected, it)
                renderFilterStyle(filterBinding.fastFood, state.fastFoodSelected, it)
//                renderFilterStyle(filterBinding.sitDown, state.sitDownSelected, it)
            }
        }
    }
}