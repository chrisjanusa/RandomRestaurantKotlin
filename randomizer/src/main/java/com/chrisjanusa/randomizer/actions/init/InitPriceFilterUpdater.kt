package com.chrisjanusa.randomizer.actions.init

import com.chrisjanusa.base_randomizer.RandomizerState

class InitPriceFilterUpdater(private val tempPrice1: Boolean, private val tempPrice2: Boolean,
                             private val tempPrice3: Boolean, private val tempPrice4: Boolean) :
    com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(price1TempSelected = tempPrice1, price2TempSelected = tempPrice2,
            price3TempSelected = tempPrice3, price4TempSelected = tempPrice4)
    }

}