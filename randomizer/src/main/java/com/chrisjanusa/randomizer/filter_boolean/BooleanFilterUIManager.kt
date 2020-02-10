package com.chrisjanusa.randomizer.filter_boolean

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.filter_base.FilterHelper.renderFilterStyle
import com.chrisjanusa.randomizer.filter_boolean.actions.FavoriteClickedAction
import com.chrisjanusa.randomizer.filter_boolean.actions.OpenNowClickedAction
import kotlinx.android.synthetic.main.filters.*

object BooleanFilterUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            open_now.setOnClickListener { sendAction(OpenNowClickedAction(), randomizerViewModel) }
            favorites_only.setOnClickListener { sendAction(FavoriteClickedAction(), randomizerViewModel) }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            context?.let {
                renderFilterStyle(open_now, state.openNowSelected, it)
                renderFilterStyle(favorites_only, state.favoriteOnlySelected, it)
            }
        }
    }
}