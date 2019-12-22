package com.chrisjanusa.randomizer.actions.filter.category

import com.chrisjanusa.randomizer.actions.base.BaseUpdater
import com.chrisjanusa.randomizer.helpers.CategoryHelper
import com.chrisjanusa.randomizer.models.RandomizerState

class TempCategoryUpdater(private val categorySet: HashSet<CategoryHelper.Category>) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(categoryTempSet = categorySet)
    }
}