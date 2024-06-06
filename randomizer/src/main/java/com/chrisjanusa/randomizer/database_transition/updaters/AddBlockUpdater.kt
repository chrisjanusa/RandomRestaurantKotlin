package com.chrisjanusa.randomizer.database_transition.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.restaurant.Restaurant
import java.util.LinkedList

class AddBlockUpdater(
    private val restaurant: Restaurant
) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newList = LinkedList(prevState.blockList)
        newList.add(restaurant)
        return prevState.copy(blockList = newList)
    }
}