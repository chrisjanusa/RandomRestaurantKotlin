package com.chrisjanusa.randomizer.filter_category.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category

class TempCategoryAddUpdater(private val category: Category) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = prevState.categoryTempSet
        newSet.add(category)
        return prevState.copy(categoryTempSet = newSet)
    }
}