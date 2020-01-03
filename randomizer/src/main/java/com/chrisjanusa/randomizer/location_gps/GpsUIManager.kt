package com.chrisjanusa.randomizer.location_gps

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.CommunicationHelper
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.location_gps.actions.GpsClickAction
import com.chrisjanusa.randomizer.location_search.actions.SearchGainFocusAction
import kotlinx.android.synthetic.main.search_card.*

object GpsUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.run {
            gps_button.setOnClickListener { activity?.let { sendAction(GpsClickAction(it), randomizerViewModel) } }
        }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {
        fragment.run {
            gps_button.isChecked = state.gpsOn
        }
    }
}