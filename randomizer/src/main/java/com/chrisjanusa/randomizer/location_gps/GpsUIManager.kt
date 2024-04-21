package com.chrisjanusa.randomizer.location_gps

import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.interfaces.FeatureUIManager
import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.RandomizerFragmentDetails
import com.chrisjanusa.randomizer.location_gps.actions.GpsClickAction

object GpsUIManager : FeatureUIManager {

    override fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            baseFragmentDetails.fragment.run {
                val gpsBinding = baseFragmentDetails.binding.locationContainer.searchCard.gpsButton
                gpsBinding.setOnClickListener { activity?.let { sendAction(GpsClickAction(it), randomizerViewModel) } }
            }
        }
    }

    override fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails) {
        if (baseFragmentDetails is RandomizerFragmentDetails) {
            val gpsBinding = baseFragmentDetails.binding.locationContainer.searchCard.gpsButton
            gpsBinding.isChecked = state.gpsOn
        }
    }
}