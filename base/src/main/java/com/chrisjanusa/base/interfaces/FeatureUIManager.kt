package com.chrisjanusa.base.interfaces

import androidx.fragment.app.Fragment
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel

interface FeatureUIManager {
    fun init(randomizerViewModel: RandomizerViewModel, fragment: BaseRestaurantFragment)
    fun render(state: RandomizerState, fragment: Fragment)
}