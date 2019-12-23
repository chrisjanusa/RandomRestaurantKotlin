package com.chrisjanusa.randomizer.actions.filter.category

import com.chrisjanusa.randomizer.helpers.CategoryHelper
import com.chrisjanusa.base_randomizer.RandomizerState

class TempCategoryUpdater(private val categorySet: HashSet<CategoryHelper.Category>) :
    com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(categoryTempSet = categorySet)
    }
}