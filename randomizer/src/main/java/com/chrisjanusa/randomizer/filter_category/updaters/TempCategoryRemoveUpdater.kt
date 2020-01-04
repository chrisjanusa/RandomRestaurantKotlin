package com.chrisjanusa.randomizer.filter_category.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category

class TempCategoryRemoveUpdater(private val category: Category) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val newSet = prevState.categoryTempSet
        newSet.remove(category)
        return prevState.copy(categoryTempSet = newSet)
    }
}