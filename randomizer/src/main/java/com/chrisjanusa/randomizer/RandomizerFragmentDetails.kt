package com.chrisjanusa.randomizer

import com.chrisjanusa.base.interfaces.BaseFragmentDetails
import com.chrisjanusa.randomizer.databinding.RandomizerFragBinding

data class RandomizerFragmentDetails(val randomizerFragment: RandomizerFragment, val binding: RandomizerFragBinding): BaseFragmentDetails(randomizerFragment) {
    fun getFiltersBinding() = binding.locationContainer.filters
}