package com.chrisjanusa.randomizer.yelp

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.base.interfaces.FeatureUIManager
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import com.chrisjanusa.randomizer.yelp.actions.RandomizeAction
import kotlinx.android.synthetic.main.bottom_overlay.*

object YelpUIManager : FeatureUIManager {
    override fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment) {
        fragment.random.setOnClickListener { sendAction(RandomizeAction(), randomizerViewModel) }
    }

    override fun render(state: RandomizerState, fragment: RandomizerFragment) {

    }

}