package com.chrisjanusa.randomizer.filter_category.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category

class SelectedCategoryUpdater(private val categorySet: HashSet<Category>, private val display: String) :
    BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(categorySet = categorySet, categoryString = display)
    }
}