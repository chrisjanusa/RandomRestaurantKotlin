package com.chrisjanusa.restaurant_base.restaurant_block.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.restaurant.Restaurant
import java.util.*

class BlockUpdater(private val restaurant: Restaurant, private val toggle: Boolean = true) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newList = LinkedList(prevState.blockList)
        if (toggle && newList.contains(restaurant)) {
            newList.remove(restaurant)
        } else if (!newList.contains(restaurant)){
            newList.addFirst(restaurant)
        }
        return prevState.copy(blockList = newList)
    }
}