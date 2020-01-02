package com.chrisjanusa.randomizer.filter_category.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.filter_category.CategoryHelper
import com.chrisjanusa.randomizer.base.models.RandomizerState

class TempCategoryUpdater(private val categorySet: HashSet<CategoryHelper.Category>) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(categoryTempSet = categorySet)
    }
}