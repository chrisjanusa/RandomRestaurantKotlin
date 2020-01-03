package com.chrisjanusa.randomizer.base.interfaces

import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel

interface FeatureUIManager {
    fun init(randomizerViewModel: RandomizerViewModel, fragment: RandomizerFragment)
    fun render(state: RandomizerState, fragment: RandomizerFragment)
}