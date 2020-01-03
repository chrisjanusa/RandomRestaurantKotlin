package com.chrisjanusa.randomizer.location_base

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.CommunicationHelper
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.location_search.actions.SearchGainFocusAction
import kotlinx.android.synthetic.main.search_card.*

object LocationUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            current_location.setOnClickListener {
                CommunicationHelper.sendAction(
                    SearchGainFocusAction(randomizerViewModel.state.value?.addressSearchString ?: ""),
                    randomizerViewModel
                )
            }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            current_text.text = state.locationText
            setMap(state.location.takeUnless { LocationHelper.isDefault(it) } ?: LocationHelper.defaultMapLocation)
        }
    }
}