package com.chrisjanusa.randomizer.actions.filter.category

import com.chrisjanusa.randomizer.helpers.CategoryHelper.Category
import com.chrisjanusa.base_randomizer.RandomizerState

class SelectedCategoryUpdater(private val categorySet: HashSet<Category>, private val display: String) :
    com.chrisjanusa.base_randomizer.BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(categorySet = categorySet, categoryString = display)
    }
}