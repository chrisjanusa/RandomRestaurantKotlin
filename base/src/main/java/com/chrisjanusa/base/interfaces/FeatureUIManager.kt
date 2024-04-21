package com.chrisjanusa.base.interfaces

import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.RandomizerViewModel

interface FeatureUIManager {
    fun init(randomizerViewModel: RandomizerViewModel, baseFragmentDetails: BaseFragmentDetails)
    fun render(state: RandomizerState, baseFragmentDetails: BaseFragmentDetails)
}