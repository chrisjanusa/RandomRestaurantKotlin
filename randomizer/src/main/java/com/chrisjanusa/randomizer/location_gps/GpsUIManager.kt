package com.chrisjanusa.randomizer.location_gps

import androidx.fragment.app.Fragment
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.location_gps.actions.GpsClickAction
import kotlinx.android.synthetic.main.search_card.*

object GpsUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, fragment: BaseRestaurantFragment) {
        fragment.run {
            gps_button.setOnClickListener { activity?.let { sendAction(GpsClickAction(it), randomizerViewModel) } }
        }
    }

    override fun render(state: RandomizerState, fragment: Fragment) {
        fragment.run {
            gps_button.isChecked = state.gpsOn
        }
    }
}